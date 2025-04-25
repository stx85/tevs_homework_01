package fh.bswe.statusserver.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "test1", groupId = "testgroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}