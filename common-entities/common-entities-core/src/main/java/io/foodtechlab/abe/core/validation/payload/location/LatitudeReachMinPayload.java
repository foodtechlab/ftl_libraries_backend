package io.foodtechlab.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.core.entities.exception.CommonErrorReason;
import io.foodtechlab.core.entities.exception.Domain;

public class LatitudeReachMinPayload extends ValidationPayload {
    public LatitudeReachMinPayload() {
        super(Domain.LOCATION.name(), CommonErrorReason.LATITUDE_REACH_MIN.name());
    }
}
