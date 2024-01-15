package io.foodtechlab.microservice.integration.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.rest.api.spring.commons.jackson.datetime.InstantDeserializer;
import com.rcore.rest.api.spring.commons.jackson.datetime.InstantSerializer;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Instant;

public class ExampleConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
        return new FeignFormatterRegistrar() {
            @Override
            public void registerFormatters(FormatterRegistry formatterRegistry) {
                DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
                registrar.setUseIsoFormat(true);
                registrar.registerFormatters(formatterRegistry);
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializerByType(Instant.class, new InstantSerializer());
        builder.deserializerByType(Instant.class, new InstantDeserializer());

        return builder.build();
    }
}
