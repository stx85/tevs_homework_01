package fh.bswe.statusserver.service;

import fh.bswe.statusserver.dto.StatusDto;
import fh.bswe.statusserver.helper.StatusServerInfo;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;

@Service
public class ServerRequestService {
    private final StatusService statusService;

    private final HttpClient httpClient = HttpClient.create()
            .followRedirect(true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofSeconds(5))
            .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(10))
                    .addHandlerLast(new WriteTimeoutHandler(10))
            );

    public WebClient client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

    public ServerRequestService(StatusService statusService) {
        this.statusService = statusService;
    }

    public void fetchStatus(StatusServerInfo serverInfo) {
        List<StatusDto> status = serverRequest(serverInfo);

        try {
            statusService.setAllStatus(status);
        } catch (Exception e) {
            System.out.println("fetchStatus error: " + e.getMessage());
        }
    }

    public List<StatusDto> serverRequest(StatusServerInfo serverInfo) {
        try {
            return client
                    .get()
                    .uri("http://" + serverInfo.getHost() + ":" + serverInfo.getPort() + "/status/all")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<StatusDto>>() {})
                    .block();
        } catch (Exception e) {
            System.out.println("ServerRequest error: " + e.getMessage());
        }

        return null;
    }
}
