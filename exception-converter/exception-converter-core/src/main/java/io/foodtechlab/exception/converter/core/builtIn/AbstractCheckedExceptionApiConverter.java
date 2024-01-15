package io.foodtechlab.exception.converter.core.builtIn;

import io.foodtechlab.exception.converter.core.CheckedExceptionApiConverter;
import io.foodtechlab.exception.converter.domain.CheckedDomainException;
import lombok.Getter;
import net.jodah.typetools.TypeResolver;

@Getter
public abstract class AbstractCheckedExceptionApiConverter<DOMAIN extends CheckedDomainException, DATA> implements CheckedExceptionApiConverter<DOMAIN, DATA> {
    protected final String domain;
    protected final String reason;

    protected final Class<DOMAIN> domainExceptionType;
    protected final Class<DATA> dataType;

    @SuppressWarnings("unchecked")
    public AbstractCheckedExceptionApiConverter(String domain, String reason) {
        this.domain = domain;
        this.reason = reason;

        Class<?>[] classes = TypeResolver.resolveRawArguments(AbstractCheckedExceptionApiConverter.class, getClass());

        domainExceptionType = (Class<DOMAIN>) classes[0];
        dataType = (Class<DATA>) classes[1];
    }
}
