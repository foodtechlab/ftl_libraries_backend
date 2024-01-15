package io.foodtechlab.channel.messaging.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class AllTrustedPackagesJsonDeserializer<T> extends JsonDeserializer<T> {
    public AllTrustedPackagesJsonDeserializer(ObjectMapper objectMapper) {
        super(objectMapper);
        super.addTrustedPackages("*");
    }
}
