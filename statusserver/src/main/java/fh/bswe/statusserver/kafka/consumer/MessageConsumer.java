package fh.bswe.statusserver.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fh.bswe.statusserver.dto.StatusDto;
import fh.bswe.statusserver.service.StatusService;
import fh.bswe.statusserver.manager.SyncStatusManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageConsumer {

    private List<StatusDto> currentStatus = new ArrayList<>();
    private final ObjectMapper objectMapper;
    private final StatusService statusService;
    private final SyncStatusManager syncStatusManager;

    public MessageConsumer(ObjectMapper objectMapper,
                           StatusService statusService,
                           SyncStatusManager syncStatusManager) {
        this.objectMapper = objectMapper;
        this.statusService = statusService;
        this.syncStatusManager = syncStatusManager;
    }

    @KafkaListener(topics = "status", groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    public void listen(String message) {
        try {
            System.out.println("Received message \"status\": " + message);
            StatusDto status = objectMapper.readValue(message, StatusDto.class);

            if (!currentStatus.contains(status)) {
                System.out.println("Added status: " + currentStatus);
                statusService.setStatus(status);
            } else {
                System.out.println("Already added status: " + currentStatus);
                currentStatus.remove(status);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            syncStatusManager.markOutOfSync();
        }
    }

    public void setStatus(StatusDto status) {
        currentStatus.add(status);
    }
}