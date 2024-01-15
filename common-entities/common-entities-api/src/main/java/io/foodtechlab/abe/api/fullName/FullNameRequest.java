package io.foodtechlab.api.fullName;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.foodtechlab.core.entities.FullName;
import io.foodtechlab.core.inputValues.FullNameInputValues;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Api("Полное имя: модель запроса")
public class FullNameRequest {
    @ApiModelProperty("Имя")
    private String firstName;
    @ApiModelProperty("Отчество")
    private String middleName;
    @ApiModelProperty("Фамилия")
    private String lastName;

    public static FullNameRequest of(FullName fullName) {
        return new FullNameRequest(
                fullName.getFirstName(),
                fullName.getMiddleName(),
                fullName.getLastName()
        );
    }

    public FullName toEntity() {
        return new FullName(firstName, middleName, lastName);
    }

    public FullNameInputValues toInputValues() {
        return FullNameInputValues.of(firstName, middleName, lastName);
    }
}
