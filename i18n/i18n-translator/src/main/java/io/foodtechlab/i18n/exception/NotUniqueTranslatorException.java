package io.foodtechlab.i18n.exception;

public class NotUniqueTranslatorException extends IllegalArgumentException {
    public NotUniqueTranslatorException() {
        super("Translator passed is not unique. You tried to add a translator twice with the same locale");
    }
}
