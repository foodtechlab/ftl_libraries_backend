package io.foodtechlab.channel.messaging.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.FailedRecordProcessor;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@RequiredArgsConstructor
@Configuration
public class KafkaMessageConsumerConfig {

    @Bean
    public AllTrustedPackagesJsonDeserializer<?> allTrustedPackagesJsonDeserializer(
            ObjectMapper objectMapper
    ) {
        return new AllTrustedPackagesJsonDeserializer<>(objectMapper);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ObjectMapper objectMapper,
            ConsumerFactory consumerFactory,
            ErrorHandler errorHandler
    ) {
        var c = new ConcurrentKafkaListenerContainerFactory<>();
        c.setErrorHandler(new SeekToCurrentErrorHandler());
        c.setConsumerFactory(consumerFactory);
        c.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        c.setErrorHandler(errorHandler);
        return c;
    }

    @ConditionalOnMissingBean(SeekToCurrentErrorHandler.class)
    @Bean
    public SeekToCurrentErrorHandler seekToCurrentErrorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
        return new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer, new FixedBackOff());
    }

    @ConditionalOnMissingBean(DeadLetterPublishingRecoverer.class)
    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate kafkaTemplate) {
        return new DeadLetterPublishingRecoverer((KafkaOperations<? extends Object, ? extends Object>) kafkaTemplate);
    }

    @ConditionalOnMissingBean(BackOff.class)
    @Bean
    public BackOff backOff() {
        return new FixedBackOff(3, 3);
    }

}
