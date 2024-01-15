package io.foodtechlab.exception;

public class DefaultTranslatorNotSetException extends IllegalStateException {
    public DefaultTranslatorNotSetException() {
        super("Default translator not set");
    }
}
