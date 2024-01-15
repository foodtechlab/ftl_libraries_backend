package io.foodtechlab.common.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;
import io.foodtechlab.common.core.entities.exception.Domain;

public class LongitudeReachMinPayload extends ValidationPayload {
    public LongitudeReachMinPayload() {
        super(Domain.LOCATION.name(), CommonErrorReason.LONGITUDE_REACH_MIN.name());
    }
}
