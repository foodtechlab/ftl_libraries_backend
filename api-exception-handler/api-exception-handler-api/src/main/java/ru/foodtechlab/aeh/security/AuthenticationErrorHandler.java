package io.foodtechlab.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.domain.commons.exception.DomainException;
import com.rcore.domain.commons.exception.ForbiddenDomainException;
import com.rcore.domain.commons.exception.UnauthorizedDomainException;
import com.rcore.rest.api.commons.exception.HttpCommunicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import io.foodtechlab.ErrorFactory;
import io.foodtechlab.core.Error;
import io.foodtechlab.core.ErrorApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AuthenticationErrorHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final ErrorFactory errorFactory;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletRequest.setCharacterEncoding("UTF-8");
        Locale locale = httpServletRequest.getLocale();

        int statusCode = 500;

        if (e.getCause() != null && e.getCause() instanceof HttpCommunicationException) {
            var httpCommunicationException = (HttpCommunicationException) e.getCause();
            ErrorApiResponse<Error> errorApiResponse = objectMapper.convertValue(httpCommunicationException.getResponse(), new TypeReference<>() {
            });
            var errorResponse = objectMapper.writeValueAsString(httpCommunicationException.getResponse());
            httpServletResponse.getWriter().write(errorResponse);
            httpServletResponse.setStatus(errorApiResponse.getStatus());
            return;
        }

        if (e.getCause() != null) {
            if (e.getCause() instanceof UnauthorizedDomainException)
                statusCode = 401;
            if (e.getCause() instanceof ForbiddenDomainException)
                statusCode = 403;
        }

        httpServletResponse.setStatus(statusCode);

        if (statusCode != 500) {
            DomainException domainException = (DomainException) e.getCause();
            httpServletResponse.getWriter()
                    .write(objectMapper
                            .writeValueAsString(ErrorApiResponse.of(
                                    domainException.getErrors()
                                            .stream()
                                            .map(error -> errorFactory.buildByError(error, locale))
                                            .collect(Collectors.toList()),
                                    statusCode,
                                    httpServletRequest.getRequestURI(),
                                    errorFactory.getTraceId()
                            )));
            return;
        }

        ErrorApiResponse<Error> responseBody = ErrorApiResponse.internalServerError(
                Collections.singletonList(errorFactory.buildUnknownException(e, locale)),
                httpServletRequest.getRequestURI(),
                errorFactory.getTraceId()
        );
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
