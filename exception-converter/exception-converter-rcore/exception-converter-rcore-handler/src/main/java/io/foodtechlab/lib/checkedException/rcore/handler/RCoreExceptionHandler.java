package ru.foodtechlab.lib.checkedException.rcore.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.foodtechlab.aeh.core.Error;
import io.foodtechlab.aeh.core.ErrorApiResponse;
import ru.foodtechlab.lib.checkedException.rcore.domain.exceptions.HandledRCoreDomainException;
import ru.foodtechlab.lib.checkedException.rcore.resource.mappers.HandledRCoreDomainMapper;

import java.util.stream.Collectors;

@Order(0)
@RestControllerAdvice
@RequiredArgsConstructor
public class RCoreExceptionHandler {
    private final HandledRCoreDomainMapper mapper;

    @ExceptionHandler(HandledRCoreDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> onHandledRCoreDomainException(HandledRCoreDomainException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorApiResponse<>(ex.getErrors().stream().map(mapper::map).collect(Collectors.toList()), 400));
    }
}
