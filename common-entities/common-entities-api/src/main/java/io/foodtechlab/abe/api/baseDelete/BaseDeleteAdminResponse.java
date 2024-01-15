package io.foodtechlab.api.baseDelete;

import com.rcore.rest.api.commons.response.BaseAdminResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.foodtechlab.domain.entities.BaseDeleteEntity;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BaseDeleteAdminResponse extends BaseAdminResponse {
    @ApiModelProperty("Признак того что объект удалён")
    protected boolean deleted;

    /**
     * Заполнить ответ стандартными значениями
     * <p>
     * Пример
     * <pre>
     * {@code
     * fill(CityResponse.builder(),cityEntity)
     * .name(cityEntity.getName())
     * .build()
     * }
     * </pre>
     */
    public static <T extends BaseDeleteAdminResponse.BaseDeleteAdminResponseBuilder<?, ?>> T fill(T builder, BaseDeleteEntity<?> entity) {
        //noinspection unchecked
        return (T) BaseAdminResponse.fill(builder, entity)
                .deleted(entity.isDeleted());
    }
}
