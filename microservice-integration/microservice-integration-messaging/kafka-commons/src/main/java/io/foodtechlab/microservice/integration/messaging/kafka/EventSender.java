package io.foodtechlab.microservice.integration.messaging.kafka;

public interface EventSender {

    void send(String topic, Payload payload);

}
