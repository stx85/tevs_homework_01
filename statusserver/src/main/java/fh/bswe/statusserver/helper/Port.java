package fh.bswe.statusserver.helper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Port {
    @JsonProperty("$")
    private int port;

    @JsonProperty("@enabled")
    private String enabled;

    public int getPort() {
        return port;
    }

    public String getEnabled() {
        return enabled;
    }
}