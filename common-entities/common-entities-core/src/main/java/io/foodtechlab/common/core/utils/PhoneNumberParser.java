package io.foodtechlab.common.core.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.rcore.commons.utils.StringUtils;
import io.foodtechlab.common.core.entities.Country;
import io.foodtechlab.common.core.types.PhoneNumberParseErrorType;
import io.foodtechlab.common.core.types.PhoneNumberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

public class PhoneNumberParser {
    public static PhoneNumberInfo parsePhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        boolean isCountryCodeNull = false;
        String countryCode = isoTwoLetterCountryCode;
        if (isoTwoLetterCountryCode == null || isoTwoLetterCountryCode.isBlank()) {
            Optional<Country> countryOptional = Country.findByPhoneNumber(phoneNumber);
            if (countryOptional.isPresent()) {
                countryCode = countryOptional.get().getIsoTwoLetterCountryCode();
            }
            isCountryCodeNull = true;
        }
        PhoneNumberType type = PhoneNumberType.UNKNOWN;

        try {
            Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parseAndKeepRawInput(phoneNumber, countryCode);
            String formattedNumber = phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            String regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedNumber);
            type = PhoneNumberType.fromPhoneNumberUtilType(phoneNumberUtil.getNumberType(parsedNumber));
            boolean isValid = phoneNumberUtil.isValidNumberForRegion(parsedNumber, countryCode);

            PhoneNumberParseErrorType invalidReason = isValid
                    ? null
                    : (countryCode != null && countryCode.equals(regionCode)
                    ? PhoneNumberParseErrorType.fromValidationResult(phoneNumberUtil.isPossibleNumberWithReason(parsedNumber))
                    : PhoneNumberParseErrorType.INCOMPATIBLE_WITH_COUNTRY_CODE);

            return PhoneNumberInfo.builder()
                    .value(formattedNumber)
                    .isoTwoLetterCountryCode(countryCode)
                    .type(type)
                    .valid(isValid)
                    .invalidReason(invalidReason)
                    .build();
        } catch (NumberParseException e) {
            String normalizedNumber = PhoneNumberNormalizer.normalizePhoneNumber(phoneNumber);
            if (StringUtils.hasText(normalizedNumber)) {
                normalizedNumber = "+" + normalizedNumber;
            }
            PhoneNumberParseErrorType invalidReason = isCountryCodeNull && e.getErrorType().equals(NumberParseException.ErrorType.INVALID_COUNTRY_CODE)
                    ? PhoneNumberParseErrorType.ISO_TWO_LETTER_COUNTRY_CODE_IS_REQUIRED
                    : PhoneNumberParseErrorType.fromNumberParseException(e.getErrorType());

            return PhoneNumberInfo.builder()
                    .value(normalizedNumber)
                    .isoTwoLetterCountryCode(countryCode)
                    .type(type)
                    .valid(false)
                    .invalidReason(invalidReason)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PhoneNumberInfo {
        private final String value;
        private final String isoTwoLetterCountryCode;
        private final PhoneNumberType type;
        private final boolean valid;
        private final PhoneNumberParseErrorType invalidReason;
    }
}