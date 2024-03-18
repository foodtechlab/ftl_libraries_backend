package io.foodtechlab.common.core.types;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

/**
 Этот enum представляет различные типы ошибок, которые могут возникнуть при парсинге номера телефона.
 <p>
 В него входят типы ошибок из ValidationResult и NumberParseException.ErrorType, а также дополнительные типы ошибок, которые могут возникнуть при парсинге номера телефона.
 <p>
 Метод fromValidationResult(PhoneNumberUtil.ValidationResult result) преобразует результат проверки номера телефона в соответствующий тип ошибки из этого enum.
 <p>
 Метод fromNumberParseException(NumberParseException.ErrorType errorType) преобразует тип ошибки исключения NumberParseException в соответствующий тип ошибки из этого enum.
 */
public enum PhoneNumberParseErrorType {
    IS_POSSIBLE,
    IS_POSSIBLE_LOCAL_ONLY,
    INVALID_COUNTRY_CODE,
    TOO_SHORT,
    INVALID_LENGTH,
    TOO_LONG,
    INVALID_NUMBER,
    NOT_A_NUMBER,
    TOO_SHORT_AFTER_IDD,
    TOO_SHORT_NSN,
    INCOMPATIBLE_WITH_COUNTRY_CODE,
    ISO_TWO_LETTER_COUNTRY_CODE_IS_REQUIRED;

    public static PhoneNumberParseErrorType fromValidationResult(PhoneNumberUtil.ValidationResult result) {
        switch (result) {
            case IS_POSSIBLE:
                return IS_POSSIBLE;
            case IS_POSSIBLE_LOCAL_ONLY:
                return IS_POSSIBLE_LOCAL_ONLY;
            case INVALID_COUNTRY_CODE:
                return INVALID_COUNTRY_CODE;
            case TOO_SHORT:
                return TOO_SHORT;
            case INVALID_LENGTH:
                return INVALID_LENGTH;
            case TOO_LONG:
                return TOO_LONG;
            default:
                throw new IllegalArgumentException("Unknown ValidationResult: " + result);
        }
    }

    public static PhoneNumberParseErrorType fromNumberParseException(NumberParseException.ErrorType errorType) {
        switch (errorType) {
            case INVALID_COUNTRY_CODE:
                return INVALID_COUNTRY_CODE;
            case NOT_A_NUMBER:
                return NOT_A_NUMBER;
            case TOO_SHORT_AFTER_IDD:
                return TOO_SHORT_AFTER_IDD;
            case TOO_SHORT_NSN:
                return TOO_SHORT_NSN;
            default:
                throw new IllegalArgumentException("Unknown ErrorType: " + errorType);
        }
    }
}