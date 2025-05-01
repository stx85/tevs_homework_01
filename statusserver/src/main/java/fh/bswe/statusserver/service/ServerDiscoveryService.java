package fh.bswe.statusserver.service;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.jackson.EurekaJsonJacksonCodec;
import fh.bswe.statusserver.helper.EurekaJsonResponseParser;
import fh.bswe.statusserver.helper.StatusServerInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServerDiscoveryService {
    private final WebClient webClient;
    final private ServerRequestService serverRequestService;

    public ServerDiscoveryService(ServerRequestService serverRequestService) {
        this.serverRequestService = serverRequestService;
        this.webClient = WebClient.builder()
                .baseUrl("http://registry:8090/eureka")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @PostConstruct
    public void discover() {
        List<StatusServerInfo> results;

        results = EurekaJsonResponseParser.parseServerList(getStatusServers());

        if (!results.isEmpty()) {
            for (StatusServerInfo serverInfo : results) {
                System.out.println("Fetch from server: " + serverInfo.getHost() + ":" + serverInfo.getPort());
                if (serverRequestService.fetchStatus(results.getFirst())) {
                    System.out.println("Successfully fetched status from server: " + serverInfo.getHost());
                    break;
                }
            }
        }
    }

    public String getStatusServers() {
        try {
            String response = webClient.get()
                    .uri("/apps/statusserver")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;

        } catch (WebClientResponseException e) {
            System.err.println("Fehler bei Anfrage: " + e.getStatusCode());
        }

        return null;
    }
}
