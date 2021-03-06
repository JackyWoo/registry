package service.discovery;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

/**
 * Another way of Exporter & Referrer
 * 
 * @author wujianchao
 */
public class Protocol {

	private ServiceDiscovery<ServiceDetails> serviceDiscovery;

	private Map<String, ServiceProvider<ServiceDetails>> providers = new ConcurrentHashMap<String, ServiceProvider<ServiceDetails>>();

	public Protocol(CuratorFramework client, String root) throws Exception {
		JsonInstanceSerializer<ServiceDetails> serializer = new JsonInstanceSerializer<ServiceDetails>(
				ServiceDetails.class);
		serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetails.class).client(client).serializer(serializer)
				.basePath(root).build();
		serviceDiscovery.start();
	}

	public void export(ServiceInstance<ServiceDetails> serviceInstance) throws Exception {
		serviceDiscovery.registerService(serviceInstance);
	}

	public ServiceInstance<ServiceDetails> refer(String serviceName) throws Exception {
		ServiceProvider<ServiceDetails> provider = providers.get(serviceName);
		if (provider == null) {
			provider = serviceDiscovery.serviceProviderBuilder().serviceName(serviceName)
					.providerStrategy(new RandomStrategy<ServiceDetails>()).build();
			provider.start();
			providers.put(serviceName, provider);
		}

		return provider.getInstance();
	}
	
	public synchronized void close() throws IOException {
		if(serviceDiscovery != null){
			serviceDiscovery.close();
		}
	}

}
