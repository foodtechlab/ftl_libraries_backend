package io.foodtechlab.common.core.entities;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import io.foodtechlab.common.core.types.PhoneNumberParseErrorType;
import io.foodtechlab.common.core.types.PhoneNumberType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Класс, представляющий телефонный номер и предоставляющий методы для работы с ним.
 */
@Getter
@NoArgsConstructor
public class PhoneNumber {
    /**
     * Значение телефонного номера в формате E.164.
     */
    protected String value;
    /**
     * ISO 3166-1 alpha-2 код страны телефонного номера.
     */
    protected String isoTwoLetterCountryCode;
    /**
     * Тип телефонного номера (мобильный, стационарный и т.д.).
     */
    protected PhoneNumberType type;
    /**
     * Флаг, указывающий, является ли телефонный номер действительным (прошедшим валидацию на соответствие номера и страны)
     */
    protected boolean valid;
    /**
     * Содержит информацию о том, почему номер не прошел валидацию.
     * Если номер действителен, то это поле равно null.
     */
    protected PhoneNumberParseErrorType invalidReason;

    private PhoneNumber(String value, String isoTwoLetterCountryCode) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        parseAndSetPhoneNumber(value, isoTwoLetterCountryCode);
    }

    /**
     * Конструктор для десериализации объекта PhoneNumber из базы данных с помощью Spring Data.
     * Поддерживает десериализацию объекта, хранящегося в базе данных со старыми названиями полей.
     *
     * @param value                   значение телефонного номера в формате E.164 (может быть null)
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера (может быть null)
     * @param phoneNumber             телефонный номер в строковом формате (может быть null)
     * @param countryCode             название страны телефонного номера (может быть null)
     *
     * @deprecated Используйте {@link PhoneNumber#of(String, String)} или {@link PhoneNumber#of(String)}
     */
    @Deprecated
    public PhoneNumber(String value, String isoTwoLetterCountryCode, String phoneNumber, String countryCode) {
        if (value != null && isoTwoLetterCountryCode != null) {
            parseAndSetPhoneNumber(value, isoTwoLetterCountryCode);
        } else if (value != null) {
            parseAndSetPhoneNumber(value, null);
        } else if (phoneNumber != null && countryCode == null) {
            parseAndSetPhoneNumber(phoneNumber, null);
        } else if (phoneNumber != null) {
            String code;
            switch (countryCode) {
                case "RUSSIA":
                    code = "RU";
                    break;
                case "UNITED_ARAB_EMIRATES":
                    code = "AE";
                    break;
                default:
                    code = "RU";
            }
            parseAndSetPhoneNumber(phoneNumber, code);
        }
    }

    /**
     * Форматирует телефонный номер в формат полного номера России без + (7XXXXXXXXXX).
     * Если был передан номер начинающийся с 89ХХХХХХХХХ - заменяет префикс 8 на 7
     *
     * @param value телефонный номер в строковом формате
     *
     * @return отформатированный номер телефона
     *
     * @deprecated Используйте {@link PhoneNumber#formatFullRuNumber(String)}
     */
    @Deprecated
    public static String format(String value) {
        return formatFullRuNumber(value);
    }

    /**
     * Форматирует телефонный номер в формат полного номера России без + (7XXXXXXXXXX).
     * Если был передан номер начинающийся с 89ХХХХХХХХХ - заменяет префикс 8 на 7
     *
     * @param value телефонный номер в строковом формате
     *
     * @return отформатированный номер телефона
     */
    public static String formatFullRuNumber(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        String formatted = value.replaceAll("[^0-9]", "").trim();

        if (formatted.length() == 11 && formatted.startsWith("89")) {
            formatted = "7" + formatted.substring(1);
        }
        return formatted;
    }

    /**
     * Проверяет, является ли указанный номер телефона действительным.
     * Определяет страну по началу номера. Список поддерживаемых стран в {@link Country}
     *
     * @param phoneNumber телефонный номер в строковом формате
     *
     * @return true, если номер телефона действителен, иначе false
     */
    public static boolean isValid(String phoneNumber) {
        return isValidPhoneNumber(phoneNumber, null);
    }

    /**
     * Проверяет, является ли указанный номер телефона действительным для указанной страны.
     *
     * @param phoneNumber             телефонный номер в строковом формате
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     *
     * @return true, если номер телефона действителен, иначе false
     */
    public static boolean isValid(String phoneNumber, @NonNull String isoTwoLetterCountryCode) {
        return isValidPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
    }

    /**
     * Создание нового объекта PhoneNumber.
     *
     * @param phoneNumber             телефонный номер в строковом формате
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     *
     * @return новый объект PhoneNumber
     */
    public static PhoneNumber of(String phoneNumber, @NonNull String isoTwoLetterCountryCode) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }
        return new PhoneNumber(phoneNumber, isoTwoLetterCountryCode);
    }

    /**
     * Создание нового объекта PhoneNumber.
     *
     * @param phoneNumber телефонный номер в строковом формате
     *
     * @return новый объект PhoneNumber
     */
    public static PhoneNumber of(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }
        return new PhoneNumber(phoneNumber, null);
    }

    /**
     * Парсит и устанавливает телефонный номер и ISO 3166-1 alpha-2 код страны.
     * Если код страны не указан, метод пытается определить его по номеру телефона.
     * Если код страны не может быть определен, метод устанавливает значение null.
     * Если номер телефона не является допустимым номером для указанного кода страны, метод устанавливает значение
     * false для поля valid и устанавливает соответствующее значение для поля invalidReason.
     * Если происходит исключение NumberParseException, метод устанавливает значение false для поля valid
     * и устанавливает соответствующее значение для поля invalidReason.
     *
     * @param phoneNumber             телефонный номер в строковом формате
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     */
    private void parseAndSetPhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        boolean isCountryCodeNull = false;
        String countryCode = (isoTwoLetterCountryCode != null && !isoTwoLetterCountryCode.isEmpty()) ? isoTwoLetterCountryCode.toUpperCase() : isoTwoLetterCountryCode;
        String normalizedNumber = PhoneNumber.formatFullRuNumber(phoneNumber);
        if (isoTwoLetterCountryCode == null || isoTwoLetterCountryCode.isBlank()) {
            Optional<Country> countryOptional = Country.findByPhoneNumber(normalizedNumber);
            if (countryOptional.isPresent()) {
                countryCode = countryOptional.get().getIsoTwoLetterCountryCode();
            }
            isCountryCodeNull = true;
        }
        PhoneNumberType type = PhoneNumberType.UNKNOWN;

        try {
            Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parseAndKeepRawInput(normalizedNumber, countryCode);
            String formattedNumber = phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            String formattedNormalizedNumber = PhoneNumber.formatFullRuNumber(formattedNumber);
            String regionCode = phoneNumberUtil.getRegionCodeForNumber(parsedNumber);
            type = PhoneNumberType.fromPhoneNumberUtilType(phoneNumberUtil.getNumberType(parsedNumber));
            boolean isValid = phoneNumberUtil.isValidNumberForRegion(parsedNumber, countryCode);

            PhoneNumberParseErrorType invalidReason;

            if (countryCode == null && !isValid) {
                invalidReason = PhoneNumberParseErrorType.ISO_TWO_LETTER_COUNTRY_CODE_IS_REQUIRED;
            } else {
                invalidReason = isValid
                        ? null
                        : (countryCode.equals(regionCode)
                        ? PhoneNumberParseErrorType.fromValidationResult(phoneNumberUtil.isPossibleNumberWithReason(parsedNumber))
                        : PhoneNumberParseErrorType.INCOMPATIBLE_WITH_COUNTRY_CODE);
            }

            this.value = formattedNormalizedNumber;
            this.isoTwoLetterCountryCode = countryCode;
            this.type = type;
            this.valid = isValid;
            this.invalidReason = invalidReason;
        } catch (NumberParseException e) {
            PhoneNumberParseErrorType invalidReason = isCountryCodeNull && e.getErrorType().equals(NumberParseException.ErrorType.INVALID_COUNTRY_CODE)
                    ? PhoneNumberParseErrorType.ISO_TWO_LETTER_COUNTRY_CODE_IS_REQUIRED
                    : PhoneNumberParseErrorType.fromNumberParseException(e.getErrorType());

            this.value = normalizedNumber;
            this.isoTwoLetterCountryCode = countryCode;
            this.type = type;
            this.valid = false;
            this.invalidReason = invalidReason;
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        String normalizedNumber = PhoneNumber.formatFullRuNumber(phoneNumber);
        if (isoTwoLetterCountryCode == null || isoTwoLetterCountryCode.isBlank()) {
            Optional<Country> countryOptional = Country.findByPhoneNumber(normalizedNumber);
            if (countryOptional.isPresent()) {
                isoTwoLetterCountryCode = countryOptional.get().getIsoTwoLetterCountryCode();
            }
        }

        try {
            Phonenumber.PhoneNumber parsedNumber = PhoneNumberUtil.getInstance().parseAndKeepRawInput(phoneNumber, isoTwoLetterCountryCode);
            return PhoneNumberUtil.getInstance().isValidNumberForRegion(parsedNumber, isoTwoLetterCountryCode);
        } catch (NumberParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return valid == that.valid &&
                Objects.equals(value, that.value) &&
                Objects.equals(isoTwoLetterCountryCode, that.isoTwoLetterCountryCode) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isoTwoLetterCountryCode, type, valid);
    }

    @Override
    public String toString() {
        return value;
    }
}