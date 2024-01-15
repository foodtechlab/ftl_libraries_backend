package io.foodtechlab.exception.converter.core.builtIn;


import io.foodtechlab.exception.converter.domain.CheckedDomainException;

public abstract class StaticCheckedExceptionApiConverter<DOMAIN extends CheckedDomainException> extends AbstractCheckedExceptionApiConverter<DOMAIN, Void> {
    protected final DOMAIN exception;

    public StaticCheckedExceptionApiConverter(String domain, String reason, DOMAIN exception) {
        super(domain, reason);
        this.exception = exception;
    }

    @Override
    public DOMAIN readResponse(Void response) {
        return exception;
    }

    @Override
    public Void writeResponse(DOMAIN exception) {
        return null;
    }
}
