package io.foodtechlab.api.location;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.foodtechlab.core.entities.Location;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Api("Координаты: модель ответа")
public class LocationResponse {
    @ApiModelProperty("Широта")
    private double latitude;
    @ApiModelProperty("Долгота")
    private double longitude;

    public static LocationResponse of(Location location) {
        return new LocationResponse(location.getLatitude(), location.getLongitude());
    }

    public Location toEntity() {
        return Location.of(latitude, longitude);
    }
}
