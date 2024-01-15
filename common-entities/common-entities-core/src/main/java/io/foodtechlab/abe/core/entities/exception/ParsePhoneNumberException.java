package io.foodtechlab.core.entities.exception;

import com.rcore.domain.commons.exception.DomainException;
import com.rcore.domain.commons.exception.GlobalDomain;

public class ParsePhoneNumberException extends DomainException {
    public ParsePhoneNumberException() {
        super(GlobalDomain.SERVER, CommonErrorReason.PARSE_PHONE_NUMBER.name(), "Invalid phone number");
    }

    public ParsePhoneNumberException(String domain, String reason, String details) {
        super(domain, reason, details);
    }
}
