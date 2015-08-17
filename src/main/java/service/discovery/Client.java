package service.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 *
 * @author wujianchao
 */
public class Client {
	
	 public static void main(String[] args) throws Exception {
		 
	        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.ZOOKEEPER_HOSTS, new ExponentialBackoffRetry(1000, 3));
	        client.start();
	        Referrer referor = new Referrer(client, Constants.ZOOKEEPER_ROOT_APTH);

	        ServiceInstance<ServiceDetails> instance1 = referor.refer(Constants.SERVICE_DEMO_1);

	        System.out.println(instance1.buildUriSpec());
	        System.out.println(instance1.getPayload());

	        ServiceInstance<ServiceDetails> instance2 = referor.refer(Constants.SERVICE_DEMO_2);

	        System.out.println(instance2.buildUriSpec());
	        System.out.println(instance2.getPayload());

	        referor.close();
	        
	        CloseableUtils.closeQuietly(client);
	    }
	 
	 
}
