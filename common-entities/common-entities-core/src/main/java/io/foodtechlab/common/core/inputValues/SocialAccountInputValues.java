package io.foodtechlab.common.core.inputValues;

import io.foodtechlab.common.core.entities.SocialAccount;
import io.foodtechlab.common.core.validation.payload.socialAccount.InvalidSocialAccountId;
import io.foodtechlab.common.core.validation.payload.socialAccount.InvalidSocialAccountTypePayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@RequiredArgsConstructor(staticName = "of")
public class SocialAccountInputValues {
    @NotNull(payload = InvalidSocialAccountTypePayload.class)
    private final SocialAccount.SocialType type;
    @NotEmpty(payload = InvalidSocialAccountId.class)
    private final String id;

    public SocialAccount toEntity() {
        return new SocialAccount(type, id);
    }
}
