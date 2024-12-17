package com.amdocs.digital.ms.coe.dashboard.testpact.pactprovider.testpact.run;

import org.junit.Assert;

import io.fabric8.kubernetes.api.model.DoneableService;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;
import java.io.File;
import io.fabric8.kubernetes.api.model.ServicePort;

public class ServiceResolver {

	private static KubernetesClient client = new DefaultKubernetesClient();

	public static KubernetesClient getClient() {
		return client;
	}

	private static String k8sHost = null;

	public static String getK8sHost(String microserviceServiceName) {
		if (k8sHost == null) {
			if (!isInsideKubernetes()) {
				k8sHost = client.nodes().list().getItems().get(0).getStatus().getAddresses()
						.stream()
						.filter(a -> "Hostname".equals(a.getType()))
						.map(a -> a.getAddress())
						.findFirst()
						.orElse(null);
			} else {
				k8sHost = microserviceServiceName;
			}
		}
		return k8sHost;
	}

	public static boolean isInsideKubernetes() {
		return new File("/var/run/secrets/kubernetes.io/serviceaccount").exists();
	}


	private static Integer getServicePort(ServicePort servicePort) {
		return isInsideKubernetes() ? servicePort.getPort() : servicePort.getNodePort();
	}

	public static Integer findHttpServicePort(String namespace, String serviceName) {
		return findServicePort(namespace, serviceName, "http");
	}

	public static Integer findCouchBaseServicePort(String namespace, String serviceName) {
		return findServicePort(namespace, serviceName, "cb");
	}

	public static Integer findServicePort(String namespace, String serviceName, String portName) {
		Assert.assertNotNull(portName);
		Resource<Service, DoneableService> mockSeverServiceResource = client.services().inNamespace(namespace)
				.withName(serviceName);
		Service service = mockSeverServiceResource.get();
		Assert.assertNotNull("cannot find service: " + serviceName + " in namepsace: " + namespace, service);
		Integer serverPort = service.getSpec().getPorts()
				.stream().filter(p -> portName.equals(p.getName())).map(p -> getServicePort(p))
				.findFirst().orElse(null);
		Assert.assertNotNull("cannot find service port for service " + serviceName + " in namepsace: " + namespace, serverPort);
		return serverPort;
	}

}
