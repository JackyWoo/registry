package service.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

/**
 *
 * @author wujianchao
 */
public class Server {
	
	public static void main(String[] args) throws Exception {
		
		CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.ZOOKEEPER_HOSTS, new ExponentialBackoffRetry(1000, 3));
		client.start();
		
		Exporter exporter = new Exporter(client, Constants.ZOOKEEPER_ROOT_APTH);
		
		ServiceInstance<ServiceDetails> instance1 = ServiceInstance.<ServiceDetails> builder().name(Constants.SERVICE_DEMO_1)
				.port(12345).address(Constants.ZOOKEEPER_ROOT_APTH)
				.payload(new ServiceDetails("service-instance-id-1", Constants.SERVICE_PROVIDER_HOST, 8088, "com.demo." + Constants.SERVICE_DEMO_1))
				//only http or https service or none schema
				.uriSpec(new UriSpec(Constants.SERVICE_URL_SPEC))
				.build();
		
		ServiceInstance<ServiceDetails> instance2 = ServiceInstance.<ServiceDetails> builder().name(Constants.SERVICE_DEMO_2)
				.port(12346).address(Constants.SERVICE_PROVIDER_HOST)
				.payload(new ServiceDetails("service-instance-id-2", Constants.SERVICE_PROVIDER_HOST, 8089, "com.demo." + Constants.SERVICE_DEMO_2))
				.uriSpec(new UriSpec())
				.build();
		
		exporter.export(instance1);
		exporter.export(instance2);

		Thread.sleep(Integer.MAX_VALUE);
		
		exporter.close();
	}
}
