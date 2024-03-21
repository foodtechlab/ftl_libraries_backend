package io.foodtechlab.common.core.types;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

/**
 * Это перечисление представляет различные типы ошибок, которые могут возникнуть при парсинге номера телефона.
 * <p>
 * В него входят типы ошибок из {@link PhoneNumberUtil.ValidationResult} и {@link NumberParseException.ErrorType}, а также дополнительные типы ошибок, которые могут возникнуть при парсинге номера телефона.
 * <p>
 * Метод {@link #fromValidationResult(PhoneNumberUtil.ValidationResult)} преобразует результат проверки номера телефона в соответствующий тип ошибки из этого перечисления.
 * <p>
 * Метод {@link #fromNumberParseException(NumberParseException.ErrorType)} преобразует тип ошибки исключения {@link NumberParseException} в соответствующий тип ошибки из этого перечисления.
 */
public enum PhoneNumberParseErrorType {
    /**
     * Переданный номер может быть действительным номером телефона.
     */
    IS_POSSIBLE,

    /**
     * Переданный номер может быть действительным только локальным номером телефона (т.е. номером, который может быть набран
     * в пределах одного региона, но не имеет всей необходимой информации для набора из любого места внутри или за пределами страны).
     */
    IS_POSSIBLE_LOCAL_ONLY,

    /**
     * Переданный номер имеет недействительный код страны.
     */
    INVALID_COUNTRY_CODE,

    /**
     * Переданный номер короче всех действительных номеров для этого региона.
     */
    TOO_SHORT,

    /**
     * Переданный номер длиннее самого короткого действительного номера для этого региона,
     * короче самого длинного действительного номера для этого региона и не имеет длины,
     * соответствующей действительным номерам для этого региона.
     * Это также может быть возвращено в случае, если был вызван метод
     * {@code isPossibleNumberForTypeWithReason(String, PhoneNumberUtil.PhoneNumberType)} из {@link PhoneNumberUtil},
     * и нет номеров этого типа для этого региона.
     */
    INVALID_LENGTH,

    /**
     * Переданный номер длиннее всех действительных номеров для этого региона.
     */
    TOO_LONG,

    /**
     * Переданный номер не является действительным номером телефона.
     */
    INVALID_NUMBER,

    /**
     * Переданный номер не является номером телефона.
     */
    NOT_A_NUMBER,

    /**
     * Переданный номер начинался с международного префикса набора номера, но после его удаления из номера осталось меньше цифр,
     * чем может иметь любой действительный номер телефона (включая код страны).
     */
    TOO_SHORT_AFTER_IDD,

    /**
     * Переданный номер после удаления кода страны, содержит меньше цифр, чем любой действительный номер телефона.
     */
    TOO_SHORT_NSN,

    /**
     * Переданный номер телефона несовместим с переданным кодом страны.
     */
    INCOMPATIBLE_WITH_COUNTRY_CODE,

    /**
     * Для проверки данного номера телефона необходимо передать код страны в формате ISO 3166-2 alpha-2.
     */
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