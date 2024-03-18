package io.foodtechlab.common.core.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.rcore.commons.utils.StringUtils;
import io.foodtechlab.common.core.entities.Country;
import io.foodtechlab.common.core.types.PhoneNumberParseErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PhoneNumberParser {
    public static PhoneNumberInfo parsePhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        boolean isCountryCodeNull = false;
        String countryCode = isoTwoLetterCountryCode;
        if (isoTwoLetterCountryCode == null || isoTwoLetterCountryCode.isBlank()) {
            countryCode = Country.findByPhoneNumber(phoneNumber);
            isCountryCodeNull = true;
        }
        PhoneNumberUtil.PhoneNumberType type = PhoneNumberUtil.PhoneNumberType.UNKNOWN;

        try {
            Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parseAndKeepRawInput(phoneNumber, countryCode);
            String formattedNumber = phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            String regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedNumber);
            type = phoneNumberUtil.getNumberType(parsedNumber);
            boolean isValid = phoneNumberUtil.isValidNumberForRegion(parsedNumber, countryCode);

            PhoneNumberParseErrorType invalidReason = isValid
                    ? null
                    : (countryCode != null && countryCode.equals(regionCode)
                    ? PhoneNumberParseErrorType.fromValidationResult(phoneNumberUtil.isPossibleNumberWithReason(parsedNumber))
                    : PhoneNumberParseErrorType.INCOMPATIBLE_WITH_COUNTRY_CODE);

            return new PhoneNumberInfo(formattedNumber, countryCode, type, isValid, invalidReason);
        } catch (NumberParseException e) {
            String normalizedNumber = PhoneNumberUtils.normalizePhoneNumber(phoneNumber);
            if (StringUtils.hasText(normalizedNumber)) {
                normalizedNumber = "+" + normalizedNumber;
            }
            PhoneNumberParseErrorType invalidReason = isCountryCodeNull && e.getErrorType().equals(NumberParseException.ErrorType.INVALID_COUNTRY_CODE)
                    ? PhoneNumberParseErrorType.ISO_TWO_LETTER_COUNTRY_CODE_IS_REQUIRED
                    : PhoneNumberParseErrorType.fromNumberParseException(e.getErrorType());
            return new PhoneNumberInfo(normalizedNumber, countryCode, type, false, invalidReason);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PhoneNumberInfo {
        private final String value;
        private final String isoTwoLetterCountryCode;
        private final PhoneNumberUtil.PhoneNumberType type;
        private final boolean valid;
        private final PhoneNumberParseErrorType invalidReason;
    }
}