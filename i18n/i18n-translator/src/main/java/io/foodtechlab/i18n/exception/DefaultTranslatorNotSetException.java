package io.foodtechlab.i18n.exception;

public class DefaultTranslatorNotSetException extends IllegalStateException {
    public DefaultTranslatorNotSetException() {
        super("Default translator not set");
    }
}
