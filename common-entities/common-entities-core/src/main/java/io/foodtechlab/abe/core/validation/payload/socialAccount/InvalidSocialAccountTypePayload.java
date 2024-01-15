package io.foodtechlab.core.validation.payload.socialAccount;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.core.entities.exception.CommonErrorReason;
import io.foodtechlab.core.entities.exception.Domain;

public class InvalidSocialAccountTypePayload extends ValidationPayload {

    public InvalidSocialAccountTypePayload() {
        super(Domain.SOCIAL_ACCOUNT.name(), CommonErrorReason.INVALID_SOCIAL_ACCOUNT_TYPE.name());
    }
}
