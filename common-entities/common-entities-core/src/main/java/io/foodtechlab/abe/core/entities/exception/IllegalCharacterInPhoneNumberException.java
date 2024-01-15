package io.foodtechlab.core.entities.exception;

import com.rcore.domain.commons.exception.GlobalDomain;

public class IllegalCharacterInPhoneNumberException extends ParsePhoneNumberException {
    public IllegalCharacterInPhoneNumberException() {
        super(GlobalDomain.SERVER, CommonErrorReason.ILLEGAL_CHARACTER_PHONE_NUMBER.name(), "Phone number has illegal characters");
    }
}
