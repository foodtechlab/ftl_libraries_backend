package io.foodtechlab.common.core.utils;

import io.foodtechlab.common.core.entities.PhoneNumber;
import lombok.experimental.UtilityClass;

@UtilityClass
@Deprecated(forRemoval = false)
public class PhoneNumberUtils {

    @Deprecated(forRemoval = false)
    public String removeSymbols(String string) {
        return PhoneNumber.format(string);
    }
}
