package io.foodtechlab.exceptionhandler.api;

import com.rcore.domain.commons.exception.*;
import io.foodtechlab.i18n.I18NHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;
import io.foodtechlab.exceptionhandler.core.Error;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Component
public class ErrorFactory {

    private final I18NHelper i18NHelper;
    private final Tracer tracer;

    public String getTraceId() {
        Span span = tracer.currentSpan();

        if (span != null)
            return span.context().traceId();
        else
            return "unknown";
    }

    public Error buildByError(DomainException.Error error, Locale locale) {

        String reason = error.getReason();
        if (error.getReason().endsWith(GlobalReason.IS_INCORRECT_POSTFIX))
            reason = GlobalReason.IS_INCORRECT_POSTFIX;
        else if (error.getReason().endsWith(GlobalReason.IS_NOT_UNIQUE_POSTFIX))
            reason = GlobalReason.IS_NOT_UNIQUE_POSTFIX;
        else if (error.getReason().endsWith(GlobalReason.IS_REQUIRED_POSTFIX))
            reason = GlobalReason.IS_REQUIRED_POSTFIX;


        String finalReason = reason;
        var title = notNullElseGet(
                i18NHelper.getProperty(error.getDomain() + "." + error.getReason() + ".title", locale),
                () -> i18NHelper.getProperty("*" + "." + finalReason + ".title", locale));
        var message = notNullElseGet(
                i18NHelper.getProperty(error.getDomain() + "." + error.getReason() + ".message", locale),
                () -> i18NHelper.getProperty("*" + "." + finalReason + ".message", locale));

        if (error.getInvalidFieldName() != null)
            message = message.replace("{value}", error.getInvalidFieldName());

        return Error.of(title, message, error.getDomain(), error.getReason(), error.getDetails());
    }

    private String notNullElseGet(String obj, Supplier<String> supplier) {
        if (obj != null) return obj;
        else
            return supplier.get();
    }

    public Error buildByDefaultException(DomainException exception, Locale locale) {
        var error = exception.getErrors().get(0);
        String value = "";
        String reason = null;
        if (exception instanceof DefaultIncorrectValueException) {
            var e = (DefaultIncorrectValueException) exception;
            value = getLocalizedFiledOrValue(error.getDomain(), e.getInvalidFieldName(), locale);
            reason = GlobalReason.IS_INCORRECT_POSTFIX;
        } else if (exception instanceof DefaultValueIsRequiredException) {
            var e = (DefaultValueIsRequiredException) exception;
            value = getLocalizedFiledOrValue(error.getDomain(), e.getInvalidFieldName(), locale);
            reason = GlobalReason.IS_REQUIRED_POSTFIX;
        } else if (exception instanceof DefaultValueIsNotUniqueException) {
            var e = (DefaultValueIsNotUniqueException) exception;
            value = getLocalizedFiledOrValue(error.getDomain(), e.getInvalidFieldName(), locale);
            reason = GlobalReason.IS_NOT_UNIQUE_POSTFIX;
        } else if (exception instanceof DefaultResourceNotFoundException) {
            value = getLocalizedDomainOrValue(error.getDomain(), locale).toLowerCase();
            reason = GlobalReason.NOT_FOUND;
        }
        var title = i18NHelper.getProperty("*" + "." + reason + ".title", locale);
        var message = i18NHelper.getProperty("*" + "." + reason + ".message", locale).replace("{value}", value);
        return Error.of(title, message, error.getDomain(), error.getReason(), error.getDetails());
    }

    private String getLocalizedFiledOrValue(String domain, String filedName, Locale locale) {
        return Objects.requireNonNullElse(i18NHelper.getProperty(domain + "." + filedName, locale), filedName);
    }

    private String getLocalizedDomainOrValue(String domain, Locale locale) {
        return Objects.requireNonNullElse(i18NHelper.getProperty(domain, locale), domain);
    }

    public Error buildUnknownException(Throwable ex, Locale locale) {
        return Error.unknownError(i18NHelper.getProperty(GlobalDomain.SERVER + "." + GlobalReason.UNKNOWN_REASON + ".title", locale),
                i18NHelper.getProperty(GlobalDomain.SERVER + "." + GlobalReason.UNKNOWN_REASON + ".message", locale),
                ex
        );
    }

    public Error buildInvalidEnumError(String enumName, String invalidValue, List<String> validValues, Locale locale) {
        String domain = "SYSTEM";
        String details = String.format("The value '%s' is not a valid value for enum '%s'. Valid values are: %s", invalidValue, enumName, String.join(", ", validValues));
        String reason = "INVALID_ENUM_VALUE";
        String value = "";
        value = getLocalizedFiledOrValue(domain, enumName, locale);
        var title = i18NHelper.getProperty(domain + "." + reason + ".title", locale);
        var message = i18NHelper.getProperty(domain + "." + reason + ".message", locale).replace("{value}", value);
        return Error.of(title, message, domain, reason, details);
    }
}