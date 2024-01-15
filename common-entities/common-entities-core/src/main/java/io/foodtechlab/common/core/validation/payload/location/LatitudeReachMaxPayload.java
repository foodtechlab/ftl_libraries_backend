package io.foodtechlab.common.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;
import io.foodtechlab.common.core.entities.exception.Domain;

public class LatitudeReachMaxPayload extends ValidationPayload {
    public LatitudeReachMaxPayload() {
        super(Domain.LOCATION.name(), CommonErrorReason.LATITUDE_REACH_MAX.name());
    }
}
