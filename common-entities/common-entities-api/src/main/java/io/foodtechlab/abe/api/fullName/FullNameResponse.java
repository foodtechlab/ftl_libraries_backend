package io.foodtechlab.api.fullName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.foodtechlab.core.entities.FullName;
import io.foodtechlab.core.inputValues.FullNameInputValues;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel("Полное имя: модель ответа")
public class FullNameResponse {
    @Builder.Default
    @ApiModelProperty("Имя")
    private String firstName = "";
    @Builder.Default
    @ApiModelProperty("Отчество")
    private String middleName = "";
    @Builder.Default
    @ApiModelProperty("Фамилия")
    private String lastName = "";

    public FullNameResponse() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
    }

    public static FullNameResponse of(FullName fullName) {
        return new FullNameResponse(
                Optional.ofNullable(fullName.getFirstName()).orElse(""),
                Optional.ofNullable(fullName.getMiddleName()).orElse(""),
                Optional.ofNullable(fullName.getLastName()).orElse("")
        );
    }

    public FullName toEntity() {
        return new FullName(firstName, middleName, lastName);
    }

    public FullNameInputValues toInputValues() {
        return FullNameInputValues.of(firstName, middleName, lastName);
    }
}
