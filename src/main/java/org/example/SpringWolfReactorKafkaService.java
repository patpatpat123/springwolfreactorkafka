package org.example;

import io.github.springwolf.core.asyncapi.annotations.AsyncListener;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

@Service
public final class SpringWolfReactorKafkaService implements CommandLineRunner {

    private final KafkaReceiver<String, String> kafkaReceiver;

    public SpringWolfReactorKafkaService(final KafkaReceiver<String, String> receiver) {
        this.kafkaReceiver = receiver;
    }

    @Override
    @AsyncListener(operation = @AsyncOperation(channelName = "the_topic"))
    public void run(final String... args) {
        kafkaReceiver.receiveAutoAck().concatMap(message -> message).flatMap(this::processMessages).subscribe();
    }

    private Mono<String> processMessages(final ConsumerRecord<String, String> consumerRecord) {
        return Mono.just(consumerRecord).map(oneMessage -> oneMessage.value().toUpperCase());
    }

}
