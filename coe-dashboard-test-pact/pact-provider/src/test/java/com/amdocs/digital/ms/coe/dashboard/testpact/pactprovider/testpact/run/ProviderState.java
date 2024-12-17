package com.amdocs.digital.ms.coe.dashboard.testpact.pactprovider.testpact.run;

import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryScanConsistency;
import com.couchbase.client.java.query.QueryStatus;
import com.couchbase.client.java.query.QueryWarning;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;

public class ProviderState {
	private static final String ID = "id";
	private static final String CONTENT = "content";

	private final File basePath;
	private boolean didCreateIndex;
	private final Bucket bucket;
	private final Cluster cluster;


	/**
	 * @return a property for a given name.  Exception if property value is null or empty.
	 */
	static String requireProperty( String propName) {
		String retval = System.getProperty(propName);
		if ( retval == null || retval.isEmpty()) {
			throw new IllegalStateException( "The system property " + propName + " has no value");
		}
		return retval;
	}

	public ProviderState() {
		String clusterUrl = requireProperty( "couchbase.cluster.clusterUrl");
		String bucketName = requireProperty( "couchbase.providers.cbProvider.bucketName");
		ClusterOptions clusterOptions = ClusterOptions
				.clusterOptions("Administrator", "Administrator");
		cluster = Cluster.connect(clusterUrl, clusterOptions);

		bucket = cluster.bucket(bucketName);
		createPrimaryIndex();

		String baseFilePath = "providerStateBaseFilePath";
		String basePathStr = requireProperty(baseFilePath);
		basePath = new File( basePathStr);
		if ( !basePath.exists()) {
			throw new IllegalStateException(
					"The base path directory for the provider state files does not exist.  It is given by the property "
							+ baseFilePath + "=" + basePathStr);
		}
	}

	// This only exists to support the n1ql delete as the implementation of flush.
	private void createPrimaryIndex() {
		QueryResult queryResult = runN1qlQueryWithConsistencyReq("CREATE PRIMARY INDEX ON " + bucket.name() + ";");
		if ( !queryResult.metaData().status().equals(QueryStatus.SUCCESS)) {
			StringBuilder buf = new StringBuilder(
					"Failed to create a Couchbase primary index so can query for or delete all documents");
			for (QueryWarning warning : queryResult.metaData().warnings()) {
				String errMsg = warning.message();
				if ( errMsg.contains("Index #primary already exists")) {// Could not find list of error codes
					return;
				}
				buf.append(errMsg);
			}
			throw new IllegalStateException( buf.toString());
		}
		didCreateIndex = true;
	}

	// Either add some way to invoke this when the service is shutdown, or switch back to using flush().  It seems
	// http://stackoverflow.com/questions/26547532/how-to-shutdown-a-spring-boot-application-in-a-correct-way
	// may be the best way.  The question is how to get maven to invoke the shutdown URL.
	// Also how to get a call back when shutdown occurs.
	public void after() {
		if ( didCreateIndex) {
			QueryResult queryResult = runN1qlQueryWithConsistencyReq("DROP PRIMARY INDEX ON " + bucket.name() + ";");
			if ( !queryResult.metaData().status().equals(QueryStatus.SUCCESS)) {
				StringBuilder buf = new StringBuilder("Failed to drop Couchbase primary index");
				for (QueryWarning warning : queryResult.metaData().warnings()) {
					String errMsg = warning.message();
					if ( errMsg.contains("Index #primary already exists")) {// Could not find list of error codes
						return;
					}
					buf.append(errMsg);
				}
				throw new IllegalStateException( buf.toString());
			}
		}
	}

	private QueryResult runN1qlQueryWithConsistencyReq(String query) {
		return cluster.query(query, QueryOptions.queryOptions()
				.scanConsistency(QueryScanConsistency.REQUEST_PLUS)
		);
	}


	public void flush() {
		// This was timing out on my PC:   bucket.bucketManager().flush();
		// So am doing a n1ql instead
		QueryResult queryResult = runN1qlQueryWithConsistencyReq("DELETE FROM " + bucket.name() + ";");
		if ( !queryResult.metaData().status().equals(QueryStatus.SUCCESS)) {
			StringBuilder buf = new StringBuilder("Failed to delete documents from " + bucket.name());
			for (QueryWarning warning : queryResult.metaData().warnings()) {
				String errMsg = warning.message();
				if ( errMsg.contains("Index #primary already exists")) {// Could not find list of error codes
					return;
				}
				buf.append( warning.message());
			}
			throw new IllegalStateException( buf.toString());
		}
	}


	public void establishState( String given) throws Exception {
		if ( "emptyDb".equals(given)) {
			flush();
			return;
		}

		String[] relFiles = given.split(" ");
		List<File> absFiles = new ArrayList<>(relFiles.length);
		for (String relFile : relFiles) {
			absFiles.add( new File( basePath, relFile));
		}
		List<JsonObject> rawDocs = readJsons(absFiles);
		uploadDocs( rawDocs);
	}

	/**
	 * Read in the files the specified files.
	 * @param files each file should normally be a directory.  It is allowed for <i>file</i>.json to be a file.
	 * @return a list of RawJsonDocument obtained by traversing the directories and files.
	 * @throws Exception
	 */
	private List<JsonObject> readJsons(List<File> files) throws Exception {
		List<JsonObject> retval = new ArrayList<>( files.size() * 2);
		for (File file : files) {
			if ( !file.exists()) {
				File newFile = new File( file.getCanonicalPath() + ".json");
				if ( !file.exists()) {
					throw new IllegalStateException( "Neither " + file.getCanonicalPath()
							+ " nor " + file.getCanonicalPath() + ".json exists");
				}
				file = newFile;
			}
			try (Stream<Path> paths = Files.walk(Paths.get(file.toString()))) {
				paths.forEach(path -> {
					try {
						if ( !Files.isDirectory(path)) {
							String docAndMetaAsJsonStr = new String( Files.readAllBytes(path));
							// Could be optimized by using some other facility that doesn't fully parse the
							// doc into Maps.
							JsonObject jObj = JsonObject.fromJson(docAndMetaAsJsonStr);
							JsonObject rawDoc = JsonObject.create()
									.put(ID, jObj.getString(ID))
									.put(CONTENT, jObj)
									.put("cas", 17);// requests can always use 17 for each doc in the etag.
							retval.add( rawDoc);
						}
					} catch (Exception e) {
						throw new IllegalStateException( e);
					}
				});
			}
		}
		return retval;
	}

	/**
	 * Upload the specified documents.
	 * @param docs the documents to upload.
	 * @throws Exception
	 */
	private void uploadDocs(List<JsonObject> docs) throws Exception {
		flush();
		for (JsonObject doc : docs) {
			bucket.defaultCollection().insert(doc.get(ID).toString(), doc.get(CONTENT));
		}
	}

}
