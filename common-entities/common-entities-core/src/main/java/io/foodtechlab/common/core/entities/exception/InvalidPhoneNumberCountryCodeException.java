package io.foodtechlab.common.core.entities.exception;

import com.rcore.domain.commons.exception.GlobalDomain;

public class InvalidPhoneNumberCountryCodeException extends ParsePhoneNumberException {
    public InvalidPhoneNumberCountryCodeException() {
        super(GlobalDomain.SERVER, CommonErrorReason.INVALID_PHONE_NUMBER_COUNTRY_CODE.name(), "Invalid phone number country code");
    }
}
