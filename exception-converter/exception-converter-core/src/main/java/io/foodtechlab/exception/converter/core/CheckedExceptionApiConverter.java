package io.foodtechlab.exception.converter.core;


import io.foodtechlab.exception.converter.domain.CheckedDomainException;

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
