package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringWolfReactorKafkaConsumerConfiguration {

    @Bean
    public KafkaReceiver<String, String> kafkaReceiver() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "locahost:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "someidentifier");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "someidentifier");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        final ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(properties);
        return KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("the_topic")));
    }

}
