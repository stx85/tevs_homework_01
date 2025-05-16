package fh.bswe.statusserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fh.bswe.statusserver.dto.StatusDto;
import fh.bswe.statusserver.kafka.consumer.MessageConsumer;
import fh.bswe.statusserver.kafka.producer.MessageProducer;
import fh.bswe.statusserver.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    private final StatusService statusService;
    private final MessageProducer messageProducer;
    private final MessageConsumer messageConsumer;
    private final ObjectMapper objectMapper;

    public StatusController(
            StatusService statusService,
            MessageProducer messageProducer,
            ObjectMapper objectMapper,
            MessageConsumer messageConsumer) {
        this.statusService = statusService;
        this.messageProducer = messageProducer;
        this.objectMapper = objectMapper;
        this.messageConsumer = messageConsumer;
    }

    @GetMapping("all")
    public ResponseEntity<?> findAll() {
        try {
            logger.info("findAll");
            return new ResponseEntity<>(statusService.getAllStatus(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findStatusByName(@RequestParam String name) {
        try {
            return new ResponseEntity<>(statusService.getStatus(name), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("all")
    public ResponseEntity<?> saveAllStatus(@RequestBody List<StatusDto> status) {
        try {
            statusService.setAllStatus(status);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> saveStatus(@RequestBody StatusDto status) {
        try {
            StatusDto savedStatus = statusService.setStatus(status);
            messageConsumer.setStatus(savedStatus);
            messageProducer.sendMessage("status", objectMapper.writeValueAsString(status));
            return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/test")
    public ResponseEntity<?> sendMessage(@RequestParam String message) {
        try {
            messageProducer.sendMessage("test1", message);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
