package ru.foodtechlab.lib.checkedException.converter;

import ru.foodtechlab.lib.checkedException.domain.CheckedDomainException;

public interface CheckedExceptionApiConverter<DOMAIN extends CheckedDomainException, DATA> {
    String getDomain();

    String getReason();


    DOMAIN readResponse(DATA response);

    /**
     * Может вернуть null
     */
    DATA writeResponse(DOMAIN exception);


    Class<DOMAIN> getDomainExceptionType();

    Class<DATA> getDataType();
}
