package io.foodtechlab.i18n.exception;

public class TranslatorListAreEmptyException extends IllegalStateException {
    public TranslatorListAreEmptyException() {
        super("Translator list are empty");
    }
}
