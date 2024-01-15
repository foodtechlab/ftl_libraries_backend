package io.foodtechlab.api.externalLink;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.foodtechlab.domain.entities.ExternalLink;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel("Структура содержащая данные привязки внешнего id")
public class ExternalLinkApiModel {
    @ApiModelProperty("Идентификатор сущности во внешней системе")
    private String id;
    @ApiModelProperty("Название")
    private String name;
    @ApiModelProperty("Тип внешней системы")
    private String type;
    @ApiModelProperty("Идентификатор аккаунта во внешней системе")
    private String externalSystemAccountId;
    @ApiModelProperty("Дата последней синхронизации")
    private Instant lastSyncDate;


    public static ExternalLinkApiModel of(ExternalLink externalLink) {
        return ExternalLinkApiModel.builder()
                .id(externalLink.getId())
                .type(externalLink.getType())
                .name(externalLink.getName())
                .externalSystemAccountId(externalLink.getExternalSystemAccountId())
                .lastSyncDate(externalLink.getLastSyncDate())
                .build();
    }

    public ExternalLink toDomain() {
        return ExternalLink.builder()
                .id(id)
                .type(type)
                .name(name)
                .externalSystemAccountId(externalSystemAccountId)
                .lastSyncDate(lastSyncDate)
                .build();
    }
}
