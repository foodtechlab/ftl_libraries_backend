package io.foodtechlab.channel.messaging.kafka.port.impl;

import org.springframework.stereotype.Component;
import io.foodtechlab.channel.messaging.kafka.port.MessageIdGenerator;

import java.util.UUID;

@Component
public class UUIDMessageIdGenerator implements MessageIdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
