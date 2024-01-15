package io.foodtechlab.exception;

public class TranslatorHasInvalidLocaleException extends IllegalArgumentException {
    public TranslatorHasInvalidLocaleException() {
        super("Locale in this translator is null");
    }
}
