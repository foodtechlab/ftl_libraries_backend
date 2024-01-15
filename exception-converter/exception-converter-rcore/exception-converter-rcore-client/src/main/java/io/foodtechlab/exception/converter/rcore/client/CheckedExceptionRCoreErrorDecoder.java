package io.foodtechlab.exception.converter.rcore.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.domain.security.exceptions.AuthorizationErrorReason;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import io.foodtechlab.exception.converter.rcore.domain.exceptions.HandledRCoreDomainException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import io.foodtechlab.exceptionhandler.core.Error;
import io.foodtechlab.exceptionhandler.core.ErrorApiResponse;
import ru.foodtechlab.lib.auth.integration.core.AccessTokenService;
import io.foodtechlab.exception.converter.core.CheckedExceptionService;
import io.foodtechlab.exception.converter.rcore.resource.mappers.HandledRCoreDomainMapper;
import io.foodtechlab.exception.converter.rcore.resource.CheckedExceptionResponse;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckedExceptionRCoreErrorDecoder implements ErrorDecoder {
    private static final List<String> reasonsForRetry = Arrays.asList(
            AuthorizationErrorReason.ACCESS_TOKEN_EXPIRED.name(),
            AuthorizationErrorReason.ACCESS_TOKEN_INACTIVATED.name(),
            AuthorizationErrorReason.ACCESS_TOKEN_REFRESHED.name(),
            AuthorizationErrorReason.ACCESS_TOKEN_NOT_EXIST.name(),
            AuthorizationErrorReason.ACCESS_TOKEN_MODIFIED.name(),
            AuthorizationErrorReason.ACCESS_TOKEN_MALFORMED.name()
    );

    private final CheckedExceptionService checkedExceptionService;
    private final AccessTokenService accessTokenService;
    private final ObjectMapper objectMapper;
    private final HandledRCoreDomainMapper handledRCoreDomainMapper;

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        String raw = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
        response.close();

        JsonNode node = objectMapper.readTree(raw);

        // Это стандартный формат ошибок ркора
        if (node.has("errors")) {
            ErrorApiResponse<Error> errorResponse = objectMapper.convertValue(node, new TypeReference<>() {
            });
            if (response.status() == 401 || response.status() == 403) {
                if (!errorResponse.getErrors().isEmpty()) {
                    String reason = errorResponse.getErrors().get(0).getReason();
                    if (reasonsForRetry.contains(reason)) {
                        //Дропаем токен
                        accessTokenService.clearToken(response.request().url());
                        //Повторяем запрос
                        throw new RetryableException(response.status(), response.reason(), response.request().httpMethod(), null, response.request());
                    }
                }
            }
            return decode(errorResponse, raw);

        } else if (node.has("domain")) {
            CheckedExceptionResponse checkedResponse = objectMapper.convertValue(node, CheckedExceptionResponse.class);

            return checkedExceptionService.parse(checkedResponse);
        }

        log.error("Unknown exception\n{}", raw);
        return new RuntimeException("Unknown exception: " + raw);
    }

    public Exception decode(@SuppressWarnings("unused") ErrorApiResponse<Error> errorApiResponse, String raw) {
        HandledRCoreDomainException exception = new HandledRCoreDomainException(
                errorApiResponse
                        .getErrors()
                        .stream()
                        .map(handledRCoreDomainMapper::inverseMap)
                        .collect(Collectors.toList())
        );

        log.error("Handled rcore exception\n{}", raw);

        return exception;
    }
}
