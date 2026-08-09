package io.foodtechlab.microservice.integration.feign.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.rest.api.commons.exception.HttpCommunicationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.foodtechlab.exceptionhandler.core.Error;
import io.foodtechlab.exceptionhandler.core.ErrorApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.Reader;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class ExampleErrorDecoder implements ErrorDecoder {
    protected final ObjectMapper mapper;

    public Exception decode(ErrorApiResponse<Error> errorApiResponse) {
        return new HttpCommunicationException(errorApiResponse);
    }

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        Reader reader = response.body().asReader(StandardCharsets.UTF_8);
        String result = IOUtils.toString(reader);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ErrorApiResponse<Error> errorResponse = mapper.readValue(result, new TypeReference<>() {
        });
        response.close();
        reader.close();

        return decode(errorResponse);
    }
}
