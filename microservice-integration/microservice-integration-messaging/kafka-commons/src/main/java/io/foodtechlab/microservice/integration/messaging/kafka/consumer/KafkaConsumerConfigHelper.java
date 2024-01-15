package io.foodtechlab.microservice.integration.messaging.kafka.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

public class KafkaConsumerConfigHelper {

    public static <K, V> KafkaListenerContainerFactory<?> buildBatchFactory(ConsumerFactory<K, V> consumerFactory, RecordMessageConverter recordMessageConverter) {
        ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(recordMessageConverter));
        return factory;
    }

    public static <K, V> KafkaListenerContainerFactory<?> buildSingleFactory(ConsumerFactory<K, V> consumerFactory, MessageConverter messageConverter) {
        ConcurrentKafkaListenerContainerFactory<K, V> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    public static <T> ConsumerFactory<String, T> buildConsumerFactory(Map<String, Object> config, ObjectMapper objectMapper) {
        var json = new JsonDeserializer<T>(new TypeReference<T>() {}, objectMapper);
        json.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), json);
    }

    public static <K, V> KafkaListenerContainerFactory<?> buildConcurrentKafkaListenerContainerFactory() {
        return new ConcurrentKafkaListenerContainerFactory<K, V>();
    }

}
