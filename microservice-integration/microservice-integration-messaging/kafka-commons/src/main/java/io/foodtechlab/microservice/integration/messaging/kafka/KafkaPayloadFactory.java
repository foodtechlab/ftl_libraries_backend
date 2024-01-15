package io.foodtechlab.microservice.integration.messaging.kafka;

import io.foodtechlab.microservice.integration.messaging.kafka.port.MessageIdGenerator;
import io.foodtechlab.microservice.integration.messaging.kafka.port.TraceIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class KafkaPayloadFactory {

    private final MessageIdGenerator messageIdGenerator;
    private final TraceIdGenerator traceIdGenerator;

    public <T extends Payload.PayloadBuilder<?, ?>> T fill(T builder) {
        return (T) builder
                .eventId(messageIdGenerator.generate())
                .traceId(traceIdGenerator.generate())
                .timestamp(Instant.now());
    }

}
