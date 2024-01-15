package io.foodtechlab.core.utils;

import lombok.experimental.UtilityClass;
import io.foodtechlab.core.entities.PhoneNumber;

@UtilityClass
@Deprecated(forRemoval = false)
public class PhoneNumberUtils {

    @Deprecated(forRemoval = false)
    public String removeSymbols(String string) {
        return PhoneNumber.format(string);
    }
}
