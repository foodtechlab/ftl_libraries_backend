package io.foodtechlab.common.api.location;

import io.foodtechlab.common.core.entities.Location;
import io.foodtechlab.common.core.inputValues.LocationInputValue;
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
@Api("Координаты: модель запроса")
public class LocationRequest {
    @ApiModelProperty("Широта")
    private double latitude;
    @ApiModelProperty("Долгота")
    private double longitude;

    public static LocationRequest of(Location location) {
        return new LocationRequest(location.getLatitude(), location.getLongitude());
    }

    public Location toEntity() {
        return Location.of(latitude, longitude);
    }

    public LocationInputValue toInputValue() {
        return LocationInputValue.of(latitude, longitude);
    }
}
