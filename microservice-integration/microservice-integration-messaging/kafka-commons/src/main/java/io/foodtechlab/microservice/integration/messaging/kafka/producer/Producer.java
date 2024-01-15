package io.foodtechlab.microservice.integration.messaging.kafka.producer;

import io.foodtechlab.microservice.integration.messaging.kafka.EventSender;
import io.foodtechlab.microservice.integration.messaging.kafka.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class Producer<T extends Payload> {

    private final EventSender eventSender;
    private final String topic;

    public void send(T t) {
        eventSender.send(topic, t);
    }
}
