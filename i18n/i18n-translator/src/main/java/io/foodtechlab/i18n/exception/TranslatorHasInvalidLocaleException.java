package io.foodtechlab.i18n.exception;

public class TranslatorHasInvalidLocaleException extends IllegalArgumentException {
    public TranslatorHasInvalidLocaleException() {
        super("Locale in this translator is null");
    }
}
