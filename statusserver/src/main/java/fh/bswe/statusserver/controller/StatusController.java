package fh.bswe.statusserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fh.bswe.statusserver.dto.StatusDto;
import fh.bswe.statusserver.kafka.consumer.MessageConsumer;
import fh.bswe.statusserver.kafka.producer.MessageProducer;
import fh.bswe.statusserver.manager.SyncStatusManager;
import fh.bswe.statusserver.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    private final StatusService statusService;
    private final MessageProducer messageProducer;
    private final MessageConsumer messageConsumer;
    private final ObjectMapper objectMapper;
    private final SyncStatusManager syncStatusManager;

    public StatusController(
            StatusService statusService,
            MessageProducer messageProducer,
            ObjectMapper objectMapper,
            MessageConsumer messageConsumer,
            SyncStatusManager syncStatusManager) {
        this.statusService = statusService;
        this.messageProducer = messageProducer;
        this.objectMapper = objectMapper;
        this.messageConsumer = messageConsumer;
        this.syncStatusManager = syncStatusManager;
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteStatus(@RequestBody StatusDto statusDto) {
        try {
            logger.info("delete status: {}", statusDto);
            if (syncStatusManager.isOutOfSync()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server is out of sync.");
            }
            statusService.deleteStatus(statusDto);
            messageProducer.sendMessage("delete", objectMapper.writeValueAsString(statusDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("all")
    public ResponseEntity<?> findAll() {
        try {
            logger.info("findAll");
            if (syncStatusManager.isOutOfSync()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server is out of sync.");
            }
            return new ResponseEntity<>(statusService.getAllStatus(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findStatusByName(@RequestParam String name) {
        try {
            if (syncStatusManager.isOutOfSync()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server is out of sync.");
            }
            return new ResponseEntity<>(statusService.getStatus(name), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("all")
    public ResponseEntity<?> saveAllStatus(@RequestBody List<StatusDto> status) {
        try {
            if (syncStatusManager.isOutOfSync()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server is out of sync.");
            }
            statusService.setAllStatus(status);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> saveStatus(@RequestBody StatusDto status) {
        try {
            if (syncStatusManager.isOutOfSync()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server is out of sync.");
            }
            StatusDto savedStatus = statusService.setStatus(status);
            messageConsumer.setStatus(savedStatus);
            messageProducer.sendMessage("status", objectMapper.writeValueAsString(status));
            return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
