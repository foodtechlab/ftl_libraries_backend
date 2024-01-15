package io.foodtechlab.common.api.socialAccount;

import io.foodtechlab.common.core.entities.SocialAccount;
import io.foodtechlab.common.core.inputValues.SocialAccountInputValues;
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
@Api("Аккаунт социальной сети: модель запроса")
public class SocialAccountRequest {
    @ApiModelProperty("Тип социальной сети")
    private SocialAccount.SocialType type;
    @ApiModelProperty("Id социальной сети")
    private String id;

    public SocialAccount toEntity() {
        return new SocialAccount(type, id);
    }

    public SocialAccountInputValues toInputValue() {
        return SocialAccountInputValues.of(type, id);
    }
}
