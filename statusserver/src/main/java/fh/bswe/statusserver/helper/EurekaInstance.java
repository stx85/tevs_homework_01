package fh.bswe.statusserver.helper;

public class EurekaInstance {
    private String instanceId;
    private String hostName;
    private String app;
    private String ipAddr;
    private Port port;
    private String homePageUrl;

    public String getInstanceId() {
        return instanceId;
    }

    public String getHostName() {
        return hostName;
    }

    public String getApp() {
        return app;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public Port getPort() {
        return port;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }
}
