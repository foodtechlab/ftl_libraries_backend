package io.foodtechlab.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.core.entities.exception.CommonErrorReason;
import io.foodtechlab.core.entities.exception.Domain;

public class LatitudeReachMaxPayload extends ValidationPayload {
    public LatitudeReachMaxPayload() {
        super(Domain.LOCATION.name(), CommonErrorReason.LATITUDE_REACH_MAX.name());
    }
}
