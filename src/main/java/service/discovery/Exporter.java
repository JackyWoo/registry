package service.discovery;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

/**
 *
 * @author wujianchao
 */
public class Exporter {

	private ServiceDiscovery<ServiceDetails> serviceDiscovery;
	
	public Exporter(CuratorFramework client, String root) throws Exception {
		JsonInstanceSerializer<ServiceDetails> serializer = new JsonInstanceSerializer<ServiceDetails>(
				ServiceDetails.class);
		serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetails.class).client(client).serializer(serializer)
				.basePath(root).build();
		serviceDiscovery.start();
	}

	public void export(ServiceInstance<ServiceDetails> serviceInstance) throws Exception {
		serviceDiscovery.registerService(serviceInstance);
	}

	public void unexport(ServiceInstance<ServiceDetails> serviceInstance) throws Exception {
		serviceDiscovery.unregisterService(serviceInstance);
	}

	public void close() throws IOException {
		serviceDiscovery.close();
	}

}
