package fh.bswe.statusserver.helper;

public class StatusServerInfo {
    private String hostName;
    private String host;
    private int port;
    private String homePageUrl;


    public StatusServerInfo(String hostName, String host, int port, String homePageUrl) {
        this.host = host;
        this.port = port;
        this.homePageUrl = homePageUrl;
        this.hostName = hostName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }
}
