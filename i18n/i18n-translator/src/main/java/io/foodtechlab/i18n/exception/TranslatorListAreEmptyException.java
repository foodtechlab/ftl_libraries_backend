package io.foodtechlab.exception;

public class TranslatorListAreEmptyException extends IllegalStateException {
    public TranslatorListAreEmptyException() {
        super("Translator list are empty");
    }
}
