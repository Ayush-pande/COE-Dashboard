package com.amdocs.digital.ms.coe.dashboard.testpact.mockexternalservices.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.fabric8.kubernetes.api.model.ServicePort;
import io.fabric8.arquillian.kubernetes.Constants;
import io.fabric8.kubernetes.api.model.DoneableService;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceFluent.MetadataNested;
import io.fabric8.kubernetes.api.model.ServiceFluent.SpecNested;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.api.model.ServiceSpecFluent.PortsNested;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;

@RunWith(Arquillian.class)
public class CreateServicesForMocksTest {

	private static final int MICRO_SERVICE_PORT = 8080;
	private static final String MOCK_SEVER_SERVICE_NAME = "docker.image.name.mock";
	private static final Pattern SPLIT_AFTER_WORD = Pattern.compile("(.)([A-Z][a-z]+)");
	private static final Pattern SPLIT_BEFORE_WORD = Pattern.compile("([a-z0-9])([A-Z])");
	private static final String SPLIT_TO_WORD = "$1-$2";

	@ArquillianResource
	private KubernetesClient client;

	private String namespace;
	private String mockServerServiceName;
	private Service mockSeverService;
	private Map<String, Integer> serviceToPort = new HashMap<>();
	private Map<String, String> serviceToProvider = new HashMap<>();
	private String dnsName;
	private Integer mockSeverServicePort;
	private boolean insideKubernetes;

	@Test
	public void createServicesForMocksTest() throws Exception {
		namespace = System.getProperty(Constants.KUBERNETES_NAMESPACE);
		mockServerServiceName = System.getProperty(MOCK_SEVER_SERVICE_NAME);
		insideKubernetes = new File("/var/run/secrets/kubernetes.io/serviceaccount").exists();
		findMockSever();
		getMockServers();
		addMockServices();
	}

	private void findMockSever() {
		if (!insideKubernetes) {
		    dnsName = client.nodes().list().getItems().get(0).getStatus().getAddresses().stream()
		        .filter(a -> "Hostname".equals(a.getType())).map(a -> a.getAddress()).findFirst().orElse(null);
		} else {
		        dnsName = mockServerServiceName;
		}
		Resource<Service, DoneableService> mockSeverServiceResource = client.services().inNamespace(namespace)
		        .withName(mockServerServiceName);
		mockSeverService = mockSeverServiceResource.get();
		Assert.assertNotNull(mockServerServiceName + " service cannot find in namepsace: " + namespace,
		    mockSeverService);
		mockSeverServicePort = mockSeverService.getSpec().getPorts().stream().filter(p -> "http".equals(p.getName()))
		        .map(this::getPortFromService).findFirst().orElse(null);
		String portType = insideKubernetes ? "port" : "node port";
		Assert.assertNotNull(mockServerServiceName + " cannot find " + portType, mockSeverServicePort);
	}

	private void getMockServers() throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		String response = getResponseFromMockingService();
		Assert.assertNotNull("Cannot get mock servers list from " + dnsName + ":" + mockSeverServicePort, response);
		for (JsonNode node : mapper.readTree(response).get("mockServers")) {
			String provider = node.get("provider").asText();
			Assert.assertNotNull("Invalid mock server", provider);
			int port = node.get("port").asInt();
			Assert.assertNotEquals("Provider " + provider + " doesn't have valid port", 0, port);
			String service = SPLIT_AFTER_WORD.matcher(provider).replaceAll(SPLIT_TO_WORD);
			service = SPLIT_BEFORE_WORD.matcher(service).replaceAll(SPLIT_TO_WORD);
			service = service.toLowerCase();
			serviceToPort.put(service, port);
			serviceToProvider.put(service, provider);
		}
	}
	
	private Integer getPortFromService(ServicePort p) {
	    return insideKubernetes ? p.getPort() : p.getNodePort();
	}

	private String getResponseFromMockingService() {
		String response = null;
		for (int i = 0 ; i< 10 ; i++) {
			try {
				response = new RestTemplate().getForObject("http://" + dnsName + ":" + mockSeverServicePort + "/",
						String.class);
				break;
			} catch (ResourceAccessException e) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					//ignore
				}
			}
		}
		return response;
	}

	private void addMockServices() {
		NonNamespaceOperation<Service, ServiceList, DoneableService, Resource<Service, DoneableService>> services = client
				.services().inNamespace(namespace);
		Map<String, String> mockServerSelector = mockSeverService.getSpec().getSelector();
		for (String service : serviceToPort.keySet()) {
			Integer port = serviceToPort.get(service);
			DoneableService newService = services.createNew();
			MetadataNested<DoneableService> metadata = newService.editOrNewMetadata();
			metadata.withName(service);
			metadata.addToLabels(mockServerSelector);
			metadata.addToLabels("mock-provider", serviceToProvider.get(service));
			metadata.endMetadata();
			SpecNested<DoneableService> spec = newService.editOrNewSpec();
			spec.withType("NodePort");
			spec.addToSelector(mockServerSelector);
			PortsNested<SpecNested<DoneableService>> newPort = spec.addNewPort();
			newPort.editOrNewTargetPort().withIntVal(port).endTargetPort();
			newPort.withPort(MICRO_SERVICE_PORT);
			newPort.withProtocol("TCP");
			newPort.endPort();
			spec.endSpec();
			newService.done();
		}
	}
}
