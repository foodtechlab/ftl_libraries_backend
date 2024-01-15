package io.foodtechlab.channel.messaging.kafka;

public interface EventSender {

    void send(String topic, Payload payload);

}
