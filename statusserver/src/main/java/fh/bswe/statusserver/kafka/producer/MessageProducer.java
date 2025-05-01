package fh.bswe.statusserver.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        System.out.println("Sending message \"" + topic + "\": " + message);
        kafkaTemplate.send(topic, message);
    }

    public void sendOnlineStatus(String key, String message) {
        kafkaTemplate.send("online-status", key, message);
    }
}