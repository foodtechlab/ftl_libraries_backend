package io.foodtechlab.microservice.integration.messaging.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultKafkaEventSender implements EventSender {
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final KafkaMessageFactory kafkaMessageFactory;

    @Override
    public void send(String topic, Payload payload) {
        var message = kafkaMessageFactory.create(topic, payload);
        kafkaTemplate.send(message);
    }
}
