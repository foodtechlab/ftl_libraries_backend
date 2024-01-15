package io.foodtechlab.channel.messaging.kafka.producer;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

public class KafkaProducerConfigHelper {

    public static <K, V> KafkaTemplate<K, V> buildKafkaTemplate(
            Map<String, Object> config
    ) {
        var producerFactory = new DefaultKafkaProducerFactory<K, V>(config);
        return new KafkaTemplate<>(producerFactory);
    }

}
