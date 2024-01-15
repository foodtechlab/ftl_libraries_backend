package io.foodtechlab.channel.messaging.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.foodtechlab.channel.messaging.kafka.EventSender;
import io.foodtechlab.channel.messaging.kafka.Payload;

@Slf4j
@RequiredArgsConstructor
public abstract class Producer<T extends Payload> {

    private final EventSender eventSender;
    private final String topic;

    public void send(T t) {
        eventSender.send(topic, t);
    }
}
