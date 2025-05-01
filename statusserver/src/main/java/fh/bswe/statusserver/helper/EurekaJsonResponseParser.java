package fh.bswe.statusserver.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class EurekaJsonResponseParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<StatusServerInfo> parseServerList(String jsonResponse) {
        List<StatusServerInfo> serverInfos = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode applicationNode = root.path("application");
            if (!applicationNode.isMissingNode()) {
                JsonNode instancesNode = applicationNode.path("instance");

                if (instancesNode.isArray()) {
                    for (JsonNode instanceNode : instancesNode) {
                        String hostName = instanceNode.path("hostName").asText();
                        String ipAddr = instanceNode.path("ipAddr").asText();
                        int port = instanceNode.path("port").path("$").asInt();
                        String homePageUrl = instanceNode.path("homePageUrl").asText();

                        StatusServerInfo serverInfo = new StatusServerInfo(hostName, ipAddr, port, homePageUrl);
                        serverInfos.add(serverInfo);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing server list: " + e.getMessage());
        }

        return serverInfos;
    }
}
