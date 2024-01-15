package io.foodtechlab.microservice.integration.messaging.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaMessageFactory {

    @Value("${spring.application.name}")
    private String appName;

    public Message<?> create(String topic, Payload payload) {
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.SOURCE, appName)
                .setHeader(KafkaHeaders.TRACE_ID, payload.getTraceId())
                .setHeader(org.springframework.kafka.support.KafkaHeaders.TOPIC, topic)
                .build();
    }

}
