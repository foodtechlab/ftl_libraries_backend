package io.foodtechlab.common.api.socialAccount;

import io.foodtechlab.common.core.entities.SocialAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Api("Аккаунт социальной сети: модель ответа")
public class SocialAccountResponse {
    @ApiModelProperty("Тип социальной сети")
    private SocialAccount.SocialType type;
    @ApiModelProperty("Id социальной сети")
    private String id;

    public static SocialAccountResponse of(SocialAccount socialAccount) {
        return new SocialAccountResponse(socialAccount.getType(), socialAccount.getId());
    }
}
