// MicroService Specific params
env.microserviceName = "coe-dashboard"
//env.unitTestParams = "-Dtest=KubernetesIntegrationKT"
env.EmailList = 'someone@amdocs.com'
env.ReleaseVersion = '0.1.0'
env.bitbucketNotification = 'true'
env.emailNotification = 'true'
env.helmRepoRelease = 'illin5564:28080/repository/helm-release/'

env.checkmarxHighThreshold = '5'
env.checkmarxfilterPattern = '**/*.java'

// thirdparties version
env.CouchbaseVersion="5.5.1"
env.CouchbaseName="couchbase-service-5"
env.ZooKeeperVersion="3.4.8"
env.KafkaVersion="0.10.2-1.fix3"
env.MilcyVersion="1.2.2"
env.HelmNexusPlatform="http://illin4261.corp.amdocs.com:28080/repository/PLTO/com/amdocs/platform/ms-lifecycle-operator"
env.DockerProxyPlatform="illin4261.corp.amdocs.com:28090"
env.SSLEnabled = 'false'

// set User Info
if (params.BRANCH_NAME == 'master'){
	wrap([$class: 'BuildUser']) {
	    try {
	        env.BUILD_USER =  "${BUILD_USER}"
	        env.BUILD_USER_ID =  "${BUILD_USER_ID}"
	        env.BUILD_USER_EMAIL = "${BUILD_USER_EMAIL}"
	    } catch (err) {
	        env.BUILD_USER =  "admin"
	    }
	}
}
