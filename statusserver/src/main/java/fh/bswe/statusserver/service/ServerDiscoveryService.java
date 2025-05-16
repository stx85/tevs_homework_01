package fh.bswe.statusserver.service;

import fh.bswe.statusserver.helper.EurekaJsonResponseParser;
import fh.bswe.statusserver.helper.StatusServerInfo;
import fh.bswe.statusserver.manager.SyncStatusManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class ServerDiscoveryService {
    private final WebClient webClient;
    final private ServerRequestService serverRequestService;
    final private SyncStatusManager syncStatusManager;

    public ServerDiscoveryService(ServerRequestService serverRequestService,
                                  SyncStatusManager syncStatusManager) {
        this.serverRequestService = serverRequestService;
        this.syncStatusManager = syncStatusManager;
        this.webClient = WebClient.builder()
                .baseUrl("http://registry:8090/eureka")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @PostConstruct
    public void synchronize() {
        List<StatusServerInfo> results = disocverStatusServers();

        if (!results.isEmpty()) {
            for (StatusServerInfo serverInfo : results) {
                System.out.println("Fetch from server: " + serverInfo.getHost() + ":" + serverInfo.getPort());
                if (serverRequestService.fetchStatus(results.getFirst())) {
                    System.out.println("Successfully fetched status from server: " + serverInfo.getHost());
                    syncStatusManager.markInSync();
                    break;
                }
            }
        } else {
            syncStatusManager.markInSync();
        }
    }

    public List<StatusServerInfo> disocverStatusServers() {
        return EurekaJsonResponseParser.parseServerList(getStatusServers());
    }

    private String getStatusServers() {

        // simple resend of request in case of error
        for (int i = 0; i < 5; i++) {
            try {
                return webClient.get()
                        .uri("/apps/statusserver")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

            } catch (WebClientResponseException e) {
                System.err.println("Fehler bei Antwort: " + e.getStatusCode());
            } catch (WebClientRequestException e) {
                System.err.println("Fehler bei Anfrage: " + e.getMessage());
            }

            syncStatusManager.markOutOfSync();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
            }
        }

        return null;
    }
}
