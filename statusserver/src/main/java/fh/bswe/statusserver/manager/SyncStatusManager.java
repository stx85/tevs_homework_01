package fh.bswe.statusserver.manager;

import org.springframework.stereotype.Component;

@Component
public class SyncStatusManager {
    private volatile boolean outOfSync = false;

    public void markOutOfSync() {
        outOfSync = true;
    }

    public void markInSync() {
        outOfSync = false;
    }

    public boolean isOutOfSync() {
        return outOfSync;
    }
}
