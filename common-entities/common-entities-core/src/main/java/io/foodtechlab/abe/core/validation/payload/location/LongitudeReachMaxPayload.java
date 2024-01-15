package io.foodtechlab.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.core.entities.exception.CommonErrorReason;
import io.foodtechlab.core.entities.exception.Domain;

public class LongitudeReachMaxPayload extends ValidationPayload {
    public LongitudeReachMaxPayload() {
        super(Domain.LOCATION.name(), CommonErrorReason.LONGITUDE_REACH_MAX.name());
    }
}
