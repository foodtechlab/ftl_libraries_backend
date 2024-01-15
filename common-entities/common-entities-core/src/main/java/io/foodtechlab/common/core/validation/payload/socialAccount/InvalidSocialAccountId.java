package io.foodtechlab.common.core.validation.payload.socialAccount;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;
import io.foodtechlab.common.core.entities.exception.Domain;

public class InvalidSocialAccountId extends ValidationPayload {
    public InvalidSocialAccountId() {
        super(Domain.SOCIAL_ACCOUNT.name(), CommonErrorReason.INVALID_SOCIAL_ACCOUNT_ID.name());
    }
}
