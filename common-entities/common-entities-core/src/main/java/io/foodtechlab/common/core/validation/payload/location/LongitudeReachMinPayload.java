package io.foodtechlab.common.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.BaseDomain;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;

public class LongitudeReachMinPayload extends ValidationPayload {
    public LongitudeReachMinPayload() {
        super(BaseDomain.LOCATION, CommonErrorReason.LONGITUDE_REACH_MIN.name());
    }
}