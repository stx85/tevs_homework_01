package fh.bswe.statusserver.scheduler;

import fh.bswe.statusserver.manager.NetworkHealthManager;
import fh.bswe.statusserver.manager.SyncStatusManager;
import fh.bswe.statusserver.service.ServerDiscoveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatusSyncScheduler {
    private static final Logger logger = LoggerFactory.getLogger(StatusSyncScheduler.class);

    private final SyncStatusManager syncStatusManager;
    private final NetworkHealthManager networkHealthManager;
    private final ServerDiscoveryService serverDiscoveryService;

    public StatusSyncScheduler(SyncStatusManager syncStatusManager,
                               ServerDiscoveryService serverDiscoveryService,
                               NetworkHealthManager networkHealthManager) {
        this.syncStatusManager = syncStatusManager;
        this.networkHealthManager = networkHealthManager;
        this.serverDiscoveryService = serverDiscoveryService;
    }

    @Scheduled(fixedDelay = 10000)
    public void synchronize() {
        if (syncStatusManager.isOutOfSync()) {
            if (networkHealthManager.isHealthy()) {
                logger.info("StatusSyncScheduler: Synchronizing...");
                try {
                    serverDiscoveryService.synchronize();
                    syncStatusManager.markInSync();
                } catch (Exception e) {
                    logger.error("StatusSyncScheduler: Error synchronizing.", e);
                }
            }
        } else {
            logger.info("StatusSyncScheduler: Already in sync.");
        }
    }
}
