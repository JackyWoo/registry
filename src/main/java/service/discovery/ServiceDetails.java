package service.discovery;

/**
 *
 * @author wujianchao
 */
public class ServiceDetails {
	
	private String id;

    private String serviceHost;

    private int servicePort;

    private String interfaceName;

    public ServiceDetails(String id, String ServiceHost, int ServicePort, String interfaceName) {
        this.id = id;
        this.serviceHost = ServiceHost;
        this.servicePort = ServicePort;
        this.interfaceName = interfaceName;
    }

    public ServiceDetails() {
    	
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String toString() {
        return "{" + "id='" + id + "\'" +
                ", serviceHost='" + serviceHost + "\'" +
                ", servicePort=" + servicePort +
                ", interfaceName='" + interfaceName + "\'}";
    }
}
