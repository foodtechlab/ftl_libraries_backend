package io.foodtechlab.exceptionhandler.api;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rcore.domain.commons.exception.*;
import com.rcore.domain.security.exceptions.CredentialPermissionInsufficientException;
import com.rcore.rest.api.commons.exception.HttpCommunicationException;
import io.foodtechlab.common.core.entities.BaseDomain;
import io.foodtechlab.exceptionhandler.core.Error;
import io.foodtechlab.exceptionhandler.core.ErrorApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.time.Instant;
import java.util.*;
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
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleUnknownException(Exception e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.internalServerError(
                Collections.singletonList(errorFactory.buildUnknownException(e, locale)),
                request.getRequestURI(),
                errorFactory.getTraceId()
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
                request.getRequestURI(),
                errorFactory.getTraceId()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler({DefaultIncorrectValueException.class, DefaultResourceNotFoundException.class, DefaultValueIsNotUniqueException.class, DefaultValueIsRequiredException.class})
    public ResponseEntity<ErrorApiResponse<Error>> handleDefaultException(DomainException e, HttpServletRequest request, Locale locale) {
        ErrorApiResponse<Error> errorApiResponse = e instanceof DefaultResourceNotFoundException
                ? ErrorApiResponse.notFound(Collections.singletonList(errorFactory.buildByDefaultException(e, locale)), request.getRequestURI(), errorFactory.getTraceId())
                : ErrorApiResponse.badRequest(Collections.singletonList(errorFactory.buildByDefaultException(e, locale)), request.getRequestURI(), errorFactory.getTraceId()
        );
        e.printStackTrace();
        return ResponseEntity.status(errorApiResponse.getStatus())
                .body(errorApiResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorApiResponse<Error>> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request, Locale locale) {
        Throwable rootCause = e.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
            Class<?> targetType = invalidFormatException.getTargetType();

            if (targetType != null && targetType.isEnum()) {
                String enumName = targetType.getSimpleName();
                String invalidValue = invalidFormatException.getValue().toString();
                List<String> validValues = getValidEnumValues(targetType);

                String fieldName = getFieldNameFromBody(e, enumName);
                String baseDomain = BaseDomain.REQUEST_BODY;

                Error error = errorFactory.buildInvalidEnumError(fieldName, enumName, invalidValue, validValues, baseDomain, locale);

                ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.badRequest(
                        Collections.singletonList(error),
                        request.getRequestURI(),
                        errorFactory.getTraceId()
                );
                errorApiResponse.setTimestamp(Instant.now());

                return ResponseEntity.status(errorApiResponse.getStatus())
                        .body(errorApiResponse);
            }
        }

        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorApiResponse.internalServerError(
                        Collections.singletonList(errorFactory.buildUnknownException(e, locale)),
                        request.getRequestURI(),
                        errorFactory.getTraceId()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorApiResponse<Error>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request, Locale locale) {
        String fieldName = e.getName();
        String invalidValue = e.getValue() != null ? e.getValue().toString() : "null";
        Class<?> targetType = e.getRequiredType();
        if (targetType != null && targetType.isEnum()) {
            List<String> validValues = getValidEnumValues(targetType);
            String baseDomain = determineBaseDomain(request, invalidValue);

            Error error = errorFactory.buildInvalidEnumError(fieldName, targetType.getSimpleName(), invalidValue, validValues, baseDomain, locale);

            ErrorApiResponse<Error> errorApiResponse = ErrorApiResponse.badRequest(
                    Collections.singletonList(error),
                    request.getRequestURI(),
                    errorFactory.getTraceId()
            );
            errorApiResponse.setTimestamp(Instant.now());

            return ResponseEntity.status(errorApiResponse.getStatus())
                    .body(errorApiResponse);
        }

        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorApiResponse.internalServerError(
                        Collections.singletonList(errorFactory.buildUnknownException(e, locale)),
                        request.getRequestURI(),
                        errorFactory.getTraceId()
                ));
    }

    private String determineBaseDomain(HttpServletRequest request, String invalidValue) {
        if (isInvalidValueInHeaders(request, invalidValue)) {
            return BaseDomain.REQUEST_HEADER;
        } else if (isInvalidValueInPath(request, invalidValue)) {
            return BaseDomain.REQUEST_PATH;
        } else if (isInvalidValueInParams(request, invalidValue)) {
            return BaseDomain.REQUEST_PARAM;
        } else {
            return null;
        }
    }

    private boolean isInvalidValueInHeaders(HttpServletRequest request, String invalidValue) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if (headerValue != null && headerValue.equals(invalidValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInvalidValueInPath(HttpServletRequest request, String invalidValue) {
        String requestURI = request.getRequestURI();
        return requestURI.contains(invalidValue);
    }

    private boolean isInvalidValueInParams(HttpServletRequest request, String invalidValue) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            for (String value : entry.getValue()) {
                if (value.equals(invalidValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getFieldNameFromBody(HttpMessageNotReadableException e, String enumName) {
        String message = e.getMessage();
        if (message != null && message.contains("[") && message.contains("]")) {
            int startIndex = message.lastIndexOf("[") + 1;
            int endIndex = message.lastIndexOf("]");
            String fieldPath = message.substring(startIndex, endIndex);
            String fieldName = fieldPath.split("\\.")[fieldPath.split("\\.").length - 1];
            fieldName = fieldName.replaceAll("\"", "");
            return fieldName;
        }
        return enumName;
    }

    private List<String> getValidEnumValues(Class<?> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}