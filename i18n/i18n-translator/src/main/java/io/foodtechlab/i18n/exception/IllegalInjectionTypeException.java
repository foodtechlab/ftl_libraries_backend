package io.foodtechlab.exception;

public class IllegalInjectionTypeException extends IllegalArgumentException {
    public IllegalInjectionTypeException(String field) {
        super(String.format("In the localization, the field %s has the parameter hasNumberCase set to true. For such a configuration, it is necessary to pass injections in integer type", field));
    }
}
