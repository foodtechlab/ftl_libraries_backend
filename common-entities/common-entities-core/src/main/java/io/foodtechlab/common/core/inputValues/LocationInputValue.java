package io.foodtechlab.common.core.inputValues;

import com.rcore.domain.commons.usecase.UseCase;
import io.foodtechlab.common.core.entities.Location;
import io.foodtechlab.common.core.validation.payload.location.LatitudeReachMaxPayload;
import io.foodtechlab.common.core.validation.payload.location.LatitudeReachMinPayload;
import io.foodtechlab.common.core.validation.payload.location.LongitudeReachMaxPayload;
import io.foodtechlab.common.core.validation.payload.location.LongitudeReachMinPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor(staticName = "empty")
@AllArgsConstructor(staticName = "of")
public class LocationInputValue implements UseCase.InputValues {
    @Max(value = 90, payload = LatitudeReachMaxPayload.class)
    @Min(value = -90, payload = LatitudeReachMinPayload.class)
    private double latitude;
    @Max(value = 180, payload = LongitudeReachMaxPayload.class)
    @Min(value = -180, payload = LongitudeReachMinPayload.class)
    private double longitude;

    public Location toEntity() {
        return Location.of(latitude, longitude);
    }
}
