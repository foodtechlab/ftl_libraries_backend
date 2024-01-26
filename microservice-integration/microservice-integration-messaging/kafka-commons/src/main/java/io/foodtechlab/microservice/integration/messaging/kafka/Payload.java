package io.foodtechlab.microservice.integration.messaging.kafka;

import com.rcore.event.driven.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Payload {
    private String eventId;
    private String traceId;
    private Instant timestamp;

    public static <T extends Payload.PayloadBuilder<?, ?>> T fill(T builder, AbstractEvent event) {
        return (T) builder
                .eventId(event.getId())
                .traceId(event.getTraceId())
                .timestamp(event.getDate());
    }

    public abstract static class PayloadBuilder<C extends Payload, B extends Payload.PayloadBuilder<C, B>>{}
}
