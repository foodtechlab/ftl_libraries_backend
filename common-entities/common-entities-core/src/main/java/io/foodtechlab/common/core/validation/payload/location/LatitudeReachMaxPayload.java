package io.foodtechlab.common.core.validation.payload.location;

import com.rcore.domain.commons.exception.ValidationPayload;
import io.foodtechlab.common.core.entities.BaseDomain;
import io.foodtechlab.common.core.entities.exception.CommonErrorReason;

public class LatitudeReachMaxPayload extends ValidationPayload {
    public LatitudeReachMaxPayload() {
        super(BaseDomain.LOCATION, CommonErrorReason.LATITUDE_REACH_MAX.name());
    }
}