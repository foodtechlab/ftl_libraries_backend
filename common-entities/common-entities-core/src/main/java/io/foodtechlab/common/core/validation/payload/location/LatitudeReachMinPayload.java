package io.foodtechlab.common.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;
import io.foodtechlab.common.core.entities.exception.Domain;

public class LatitudeReachMinPayload extends ValidationPayload {
    public LatitudeReachMinPayload() {
        super(Domain.LOCATION.name(), CommonErrorReason.LATITUDE_REACH_MIN.name());
    }
}
