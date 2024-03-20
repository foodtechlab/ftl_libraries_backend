package io.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.types.PhoneNumberParseErrorType;
import io.foodtechlab.common.core.types.PhoneNumberType;
import io.foodtechlab.common.core.utils.PhoneNumberParser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Класс, представляющий телефонный номер и предоставляющий методы для работы с ним.
 */
@NoArgsConstructor
@Getter
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

    public PhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        setPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
    }

    /**
     * Создание нового объекта PhoneNumber.
     *
     * @param phoneNumber             телефонный номер в строковом формате
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     *
     * @return новый объект PhoneNumber
     */
    public static PhoneNumber createPhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        return new PhoneNumber(phoneNumber, isoTwoLetterCountryCode);
    }

    /**
     * Устанавливает телефонный номер и ISO 3166-1 alpha-2 код страны.
     *
     * @param phoneNumber             телефонный номер в строковом формате
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     */
    private void setPhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumberParser.PhoneNumberInfo phoneNumberInfo = PhoneNumberParser.parsePhoneNumber(phoneNumber, isoTwoLetterCountryCode);
        this.value = phoneNumberInfo.getValue();
        this.isoTwoLetterCountryCode = phoneNumberInfo.getIsoTwoLetterCountryCode();
        this.type = phoneNumberInfo.getType();
        this.valid = phoneNumberInfo.isValid();
        this.invalidReason = phoneNumberInfo.getInvalidReason();
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
}