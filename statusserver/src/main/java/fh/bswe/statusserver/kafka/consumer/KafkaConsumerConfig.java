package fh.bswe.statusserver.kafka.consumer;

import fh.bswe.statusserver.manager.SyncStatusManager;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    private final SyncStatusManager syncStatusManager;

    public KafkaConsumerConfig(SyncStatusManager syncStatusManager) {
        this.syncStatusManager = syncStatusManager;
    }


    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-server:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group-id");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        factory.setCommonErrorHandler(errorHandler());

        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        FixedBackOff backOff = new FixedBackOff(2000, 3);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, e) -> {
                    logger.error("Error processing record: " + consumerRecord, e);
                    syncStatusManager.markOutOfSync();
                },
                backOff
        );

        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class, NullPointerException.class);

        return errorHandler;
    }
}