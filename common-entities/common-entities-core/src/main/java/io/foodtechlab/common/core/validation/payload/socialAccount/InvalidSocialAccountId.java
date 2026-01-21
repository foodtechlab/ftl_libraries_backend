package io.foodtechlab.common.core.validation.payload.socialAccount;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.BaseDomain;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;

public class InvalidSocialAccountId extends ValidationPayload {
    public InvalidSocialAccountId() {
        super(BaseDomain.SOCIAL_ACCOUNT, CommonErrorReason.INVALID_SOCIAL_ACCOUNT_ID.name());
    }
}