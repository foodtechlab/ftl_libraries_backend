package io.foodtechlab.common.core.entities.exception;

import com.rcore.domain.commons.exception.GlobalDomain;

public class PhoneNumberTooShortException extends ParsePhoneNumberException {
    public PhoneNumberTooShortException() {
        super(GlobalDomain.SERVER, CommonErrorReason.PHONE_NUMBER_TOO_SHORT.name(), "Invalid phone number is too short");
    }
}
