package io.foodtechlab.exception.converter.handler;

import io.foodtechlab.exception.converter.core.CheckedExceptionService;
import io.foodtechlab.exception.converter.domain.CheckedDomainException;
import io.foodtechlab.exception.converter.rcore.resource.CheckedExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Order(0) // Иначе его перекрывает ркоровский
@Log4j2
public class CheckedExceptionHandler {
    private final CheckedExceptionService checkedExceptionService;

    @ExceptionHandler(CheckedDomainException.class)
    public ResponseEntity<CheckedExceptionResponse> checkedExceptionResponse(CheckedDomainException exception) {
        CheckedExceptionResponse response = checkedExceptionService.convert(exception);

        log.info("Checked Exception thrown" + exception.toString() + " catch");

        return ResponseEntity.badRequest().body(response);
    }
}
