package io.foodtechlab.exception.converter.domain;

import lombok.Getter;

/**
 * Эксепшн, обозначающий какое-то одно, конкретное исключение. Всегда должен быть обработан
 */
@Getter
public abstract class CheckedDomainException extends Exception {
    private final String domain;
    private final String reason;
    private final String details;

    public CheckedDomainException(String domain, String reason, String details) {
        super(domain + " " + reason + ": " + details);
        this.domain = domain;
        this.reason = reason;
        this.details = details;
    }
}
