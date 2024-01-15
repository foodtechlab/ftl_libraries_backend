package io.foodtechlab.channel.messaging.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class ObjectMapperProducerFactoryCustomizer implements DefaultKafkaProducerFactoryCustomizer {

    private final ObjectMapper objectMapper;

    @Override
    public void customize(DefaultKafkaProducerFactory<?, ?> producerFactory) {
        if (Objects.nonNull(producerFactory)) {
            producerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        }
    }
}
