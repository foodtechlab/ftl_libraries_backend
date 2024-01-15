package io.foodtechlab.tests.kafka.utils;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Utils-класс, помогающий создавать kafka-consumer для тестирования
 **/
public class KafkaTestConsumerUtils {

    public static <K, V> Consumer<K, V> buildStringSerializationConsumer(EmbeddedKafkaBroker embeddedKafka) {
        return buildConsumer(embeddedKafka, StringDeserializer.class, org.springframework.kafka.support.serializer.JsonDeserializer.class);
    }

    public static <K, V> Consumer<K, V> buildConsumer(
            EmbeddedKafkaBroker embeddedKafka,
            Class<? extends Deserializer<?>> keyDeserializer,
            Class<?> valueDeserializer
    ) {

        var consumerProps = KafkaTestUtils.consumerProps("test-consumer-" + UUID.randomUUID().toString(), "true", embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer.getName());
        return buildConsumer(consumerProps);
    }

    private static <K, V> Consumer<K, V> buildConsumer(Map<String, Object> properties) {
        final DefaultKafkaConsumerFactory<K, V> consumerFactory = new DefaultKafkaConsumerFactory<>(properties);
        return consumerFactory.createConsumer();
    }
}
