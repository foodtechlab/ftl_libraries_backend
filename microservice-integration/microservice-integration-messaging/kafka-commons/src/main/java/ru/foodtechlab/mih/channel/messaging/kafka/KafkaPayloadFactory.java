package io.foodtechlab.channel.messaging.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.foodtechlab.channel.messaging.kafka.port.MessageIdGenerator;
import io.foodtechlab.channel.messaging.kafka.port.TraceIdGenerator;

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
