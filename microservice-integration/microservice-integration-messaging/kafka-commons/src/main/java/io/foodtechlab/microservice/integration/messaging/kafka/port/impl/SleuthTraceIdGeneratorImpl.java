package io.foodtechlab.microservice.integration.messaging.kafka.port.impl;

import brave.Tracer;
import io.foodtechlab.microservice.integration.messaging.kafka.port.TraceIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class SleuthTraceIdGeneratorImpl implements TraceIdGenerator {

    private final Tracer tracer;

    @Override
    public String generate() {
        try {
            return tracer.currentSpan().context().traceIdString();
        } catch (Exception e) {
            log.error("Trace id not found");
            return UUID.randomUUID().toString();
        }
    }
}
