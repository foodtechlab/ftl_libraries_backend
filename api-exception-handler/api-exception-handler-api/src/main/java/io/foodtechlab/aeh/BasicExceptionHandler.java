package io.foodtechlab.aeh;

import com.rcore.domain.commons.exception.*;
import com.rcore.domain.security.exceptions.CredentialPermissionInsufficientException;
import com.rcore.rest.api.commons.exception.HttpCommunicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.foodtechlab.aeh.core.Error;
import io.foodtechlab.aeh.core.ErrorApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class BasicExceptionHandler {

    private final ErrorFactory errorFactory;

    @ExceptionHandler(UnauthorizedDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleUnauthorizedDomainException(UnauthorizedDomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.unauthorized(
                e.getErrors()
                        .stream()
                        .map(error -> errorFactory.buildByError(error, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(TooManyRequestsDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleTooManyRequestsDomainException(TooManyRequestsDomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.tooManyRequest(
                e.getErrors()
                        .stream()
                        .map(error -> errorFactory.buildByError(error, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(NotFoundDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleUnauthorizedDomainException(NotFoundDomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.notFound(
                e.getErrors()
                        .stream()
                        .map(error -> errorFactory.buildByError(error, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(BadRequestDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleBadRequestDomainException(BadRequestDomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.badRequest(
                e.getErrors()
                        .stream()
                        .map(error -> errorFactory.buildByError(error, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(ForbiddenDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleForbiddenDomainException(ForbiddenDomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.forbidden(
                e.getErrors()
                        .stream()
                        .map(error -> errorFactory.buildByError(error, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(HttpCommunicationException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleUnknownException(HttpCommunicationException e, HttpServletRequest request, Locale locale) {
        var response = (ErrorApiResponse<Error>) e.getResponse();
        e.printStackTrace();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, Locale locale) {
        var error = new CredentialPermissionInsufficientException();
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.forbidden(
                error.getErrors()
                        .stream()
                        .map(er -> errorFactory.buildByError(er, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(InternalServerDomainException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleInternalServerException(InternalServerDomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.internalServerError(
                e.getErrors()
                        .stream()
                        .map(error -> errorFactory.buildByError(error, locale))
                        .collect(Collectors.toList()),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleUnknownException(Exception e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.internalServerError(
                Collections.singletonList(errorFactory.buildUnknownException(e, locale)),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleUndeclaredException(UndeclaredThrowableException e, HttpServletRequest request, Locale locale) {
        Throwable ex = e.getUndeclaredThrowable();
        if (ex == null)
            ex = e;
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.internalServerError(
                Collections.singletonList(errorFactory.buildUnknownException(ex, locale)),
                request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler({DefaultIncorrectValueException.class, DefaultResourceNotFoundException.class, DefaultValueIsNotUniqueException.class, DefaultValueIsRequiredException.class})
    public ResponseEntity<ErrorApiResponse<Error>> handleDefaultException(DomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = e instanceof DefaultResourceNotFoundException
                ? ErrorApiResponse.notFound(Collections.singletonList(errorFactory.buildByDefaultException(e, locale)), request.getRequestURI())
                : ErrorApiResponse.badRequest(Collections.singletonList(errorFactory.buildByDefaultException(e, locale)), request.getRequestURI()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

}
