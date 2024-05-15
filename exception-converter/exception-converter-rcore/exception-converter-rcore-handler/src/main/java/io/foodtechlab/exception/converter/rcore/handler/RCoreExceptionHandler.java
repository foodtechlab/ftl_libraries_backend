package io.foodtechlab.exception.converter.rcore.handler;

import io.foodtechlab.exception.converter.rcore.domain.exceptions.HandledRCoreDomainException;
import io.foodtechlab.exception.converter.rcore.resource.mappers.HandledRCoreDomainMapper;
import io.foodtechlab.exceptionhandler.core.Error;
import io.foodtechlab.exceptionhandler.core.ErrorApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
