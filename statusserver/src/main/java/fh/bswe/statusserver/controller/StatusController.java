package fh.bswe.statusserver.controller;

import fh.bswe.statusserver.entity.Status;
import fh.bswe.statusserver.kafka.producer.MessageProducer;
import fh.bswe.statusserver.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;
    private final MessageProducer messageProducer;

    public StatusController(StatusService statusService, MessageProducer messageProducer) {
        this.statusService = statusService;
        this.messageProducer = messageProducer;
    }

    @GetMapping("all")
    public ResponseEntity<?> findAll() {
        try {
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
    public ResponseEntity<?> saveAllStatus(@RequestBody List<Status> status) {
        try {
            statusService.setAllStatus(status);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> saveStatus(@RequestBody Status status) {
        try {
            return new ResponseEntity<>(statusService.setStatus(status), HttpStatus.CREATED);
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
