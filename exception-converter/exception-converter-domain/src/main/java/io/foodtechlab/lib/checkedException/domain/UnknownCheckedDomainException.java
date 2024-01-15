package ru.foodtechlab.lib.checkedException.domain;

public final class UnknownCheckedDomainException extends CheckedDomainException {
    public UnknownCheckedDomainException(String domain, String reason, String details) {
        super(domain, reason, details);
    }
}
