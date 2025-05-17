package fh.bswe.statusserver.manager;

import org.springframework.stereotype.Component;

@Component
public class NetworkHealthManager {
    private boolean healthy = true;

    public boolean isHealthy() {
        return healthy;
    }

    public void setAsUnhealthy() {
        healthy = false;
    }

    public void setAsHealthy() {
        healthy = true;
    }
}
