package fh.bswe.statusserver.scheduler;

import fh.bswe.statusserver.manager.NetworkHealthManager;
import fh.bswe.statusserver.manager.SyncStatusManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Component
public class NetworkHealthScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NetworkHealthScheduler.class);

    private final SyncStatusManager syncStatusManager;
    private final NetworkHealthManager networkHealthManager;

    public NetworkHealthScheduler(SyncStatusManager syncStatusManager,
                                  NetworkHealthManager networkHealthmanager) {
        this.syncStatusManager = syncStatusManager;
        this.networkHealthManager = networkHealthmanager;
    }

    @Scheduled(fixedRate = 1500)
    public void checkNetworkHealth() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("kafka-server", 9092), 2000);
            networkHealthManager.setAsHealthy();
        } catch (IOException e) {
            logger.error("Error checking network health: " + e.getMessage());
            networkHealthManager.setAsUnhealthy();
            syncStatusManager.markOutOfSync();
        }
    }
}
