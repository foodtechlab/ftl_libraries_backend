package io.foodtechlab;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
public class I18NHelper {

    private static final String defaultCode = "UnknownException";
    private final MessageSource messageSource;

    public I18NHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(Exception e, Locale locale) {
        return getProperty(e.getClass().getSimpleName() + ".message", locale);
    }

    public String getTitle(Exception e, Locale locale) {
        return getProperty(e.getClass().getSimpleName() + ".title", locale);
    }

    public String getUnknownExceptionTitle(Locale locale) {
        return getProperty(defaultCode + ".title", locale);
    }

    public String getUnknownExceptionMessage(Locale locale) {
        return getProperty(defaultCode + ".message", locale);
    }

    public String getProperty(String code, Locale locale) {
        try {
            return messageSource.getMessage(code, null, locale);
        } catch (Exception e) {
            log.error("Get property error for locale {} \n {}", locale.getCountry(), e);
            return null;
        }
    }
}
