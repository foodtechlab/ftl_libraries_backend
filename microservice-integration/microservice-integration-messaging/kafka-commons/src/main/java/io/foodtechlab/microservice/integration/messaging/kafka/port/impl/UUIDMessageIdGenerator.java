package io.foodtechlab.microservice.integration.messaging.kafka.port.impl;

import io.foodtechlab.microservice.integration.messaging.kafka.port.MessageIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDMessageIdGenerator implements MessageIdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
