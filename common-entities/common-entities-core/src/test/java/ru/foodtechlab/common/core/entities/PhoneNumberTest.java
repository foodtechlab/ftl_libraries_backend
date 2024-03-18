package ru.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.entities.PhoneNumber;
import io.foodtechlab.common.core.types.PhoneNumberParseErrorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Класс для тестирования сущности PhoneNumber.
 */
public class PhoneNumberTest {

    /**
     * Тестирование действительных номеров телефонов с указанным кодом страны.
     *
     * @param phoneNumber             телефонный номер
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     * @param expectedFormattedNumber ожидаемый отформатированный номер телефона в формате E.164
     */
    @ParameterizedTest
    @MethodSource("validPhoneNumbers")
    void testValidPhoneNumbers(String phoneNumber, String isoTwoLetterCountryCode, String expectedFormattedNumber) {
        PhoneNumber phone = createPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
        Assertions.assertTrue(phone.isValid());
        Assertions.assertEquals(isoTwoLetterCountryCode, phone.getIsoTwoLetterCountryCode());
        Assertions.assertEquals(expectedFormattedNumber, phone.getValue());
    }

    /**
     * Тестирование недействительных номеров телефонов с указанным кодом страны.
     *
     * @param phoneNumber             телефонный номер
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     */
    @ParameterizedTest
    @MethodSource("invalidPhoneNumbers")
    void testInvalidPhoneNumbers(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumber phone = createPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
        Assertions.assertFalse(phone.isValid());
    }

    /**
     * Тестирование действительных номеров телефонов без указания кода страны.
     *
     * @param phoneNumber             телефонный номер
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     */
    @ParameterizedTest
    @MethodSource("validPhoneNumbersWithoutCountryCode")
    void testValidPhoneNumbersWithoutCountryCode(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumber phone = createPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
        Assertions.assertTrue(phone.isValid());
        Assertions.assertEquals("RU", phone.getIsoTwoLetterCountryCode());
    }

    /**
     * Тестирование недействительных кодов стран.
     *
     * @param phoneNumber             телефонный номер
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     */
    @ParameterizedTest
    @MethodSource("phoneNumbersNotExistedCountryCode")
    void testInvalidCountryCode(String phoneNumber, String isoTwoLetterCountryCode) {
        PhoneNumber phone = createPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
        Assertions.assertFalse(phone.isValid());
    }

    /**
     * Тестирование форматирования номеров телефонов с различными вариантами входных данных.
     *
     * @param phoneNumber             Оригинальный номер телефона с различными вариантами форматирования
     * @param isoTwoLetterCountryCode ISO 3166-1 alpha-2 код страны телефонного номера
     * @param expectedFormattedNumber Ожидаемый отформатированный номер телефона в формате E.164
     */
    @ParameterizedTest
    @MethodSource("phoneNumbersWithDifferentFormatting")
    void testPhoneNumberWithDifferentFormatting(String phoneNumber, String isoTwoLetterCountryCode, String expectedFormattedNumber) {
        PhoneNumber phone = createPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
        Assertions.assertEquals(expectedFormattedNumber, phone.getValue());
    }

    /**
     * Тестирование определения типа номера телефона для российских номеров.
     *
     * @param phoneNumber  номер телефона
     * @param expectedType ожидаемый тип номера телефона
     */
    @ParameterizedTest
    @MethodSource("phoneNumbersTypeDetection")
    void testPhoneNumberTypeDetection(String phoneNumber, String expectedType) {
        PhoneNumber phoneNumberObj = PhoneNumber.createPhoneNumber(phoneNumber, "RU");
        Assertions.assertEquals(expectedType, phoneNumberObj.getType().toString());
    }

    /**
     * Тестирование определения причины ошибки для некорректных номеров телефона.
     *
     * @param phoneNumber       номер телефона
     * @param countryCode       код страны
     * @param expectedErrorType ожидаемый тип ошибки
     */
    @ParameterizedTest
    @MethodSource("invalidPhoneNumbersErrorReason")
    void testInvalidPhoneNumberErrorType(String phoneNumber, String countryCode, PhoneNumberParseErrorType expectedErrorType) {
        PhoneNumber phoneNumberObj = PhoneNumber.createPhoneNumber(phoneNumber, countryCode);
        Assertions.assertFalse(phoneNumberObj.isValid());
        Assertions.assertEquals(expectedErrorType, phoneNumberObj.getInvalidReason());
    }

    @Test
    void testEqualsAndHashCode() {
        PhoneNumber phone1 = createPhoneNumber("+79023602131", "RU");
        PhoneNumber phone2 = createPhoneNumber("79023602131", "RU");
        PhoneNumber phone3 = createPhoneNumber("89023602131", "RU");
        PhoneNumber phone4 = createPhoneNumber("+79023602131", "US");

        Assertions.assertTrue(phone1.equals(phone2) && phone2.equals(phone3) && phone3.equals(phone1));
        Assertions.assertNotEquals(phone1, phone4);
        Assertions.assertEquals(phone1.hashCode(), phone2.hashCode());
        Assertions.assertEquals(phone2.hashCode(), phone3.hashCode());
        Assertions.assertEquals(phone3.hashCode(), phone1.hashCode());
        Assertions.assertNotEquals(phone1.hashCode(), phone4.hashCode());
    }

    private PhoneNumber createPhoneNumber(String phoneNumber, String isoTwoLetterCountryCode) {
        return PhoneNumber.createPhoneNumber(phoneNumber, isoTwoLetterCountryCode);
    }

    private static Stream<Arguments> validPhoneNumbers() {
        return Stream.of(
                Arguments.of("+79023602131", "RU", "+79023602131"),
                Arguments.of("79023602131", "RU", "+79023602131"),
                Arguments.of("89023602131", "RU", "+79023602131"),

                Arguments.of("+7 902 360-21-31", "RU", "+79023602131"),
                Arguments.of("7 (9023) 60-21-31", "RU", "+79023602131"),
                Arguments.of("8 902 (360)-21-31", "RU", "+79023602131"),

                Arguments.of("+7 902 360 21 31", "RU", "+79023602131"),
                Arguments.of("7 902 360 21 31", "RU", "+79023602131"),
                Arguments.of("8 902 36021 31", "RU", "+79023602131"),

                Arguments.of("+971501234567", "AE", "+971501234567"),
                Arguments.of("971501234567", "AE", "+971501234567"),
                Arguments.of("0501234567", "AE", "+971501234567")
        );
    }

    private static Stream<Arguments> validPhoneNumbersWithoutCountryCode() {
        return Stream.of(
                Arguments.of("+79023602131", null),
                Arguments.of("79023602131", null),
                Arguments.of("89023602131", null),

                Arguments.of("+7 902 360-21-31", null),
                Arguments.of("7 (9023) 60-21-31", null),
                Arguments.of("8 902 (360)-21-31", null),

                Arguments.of("+7 902 360 21 31", null),
                Arguments.of("7 902 360 21 31", null),
                Arguments.of("8 902 36021 31", null),
                Arguments.of("8 902 36021 31", "")
        );
    }

    private static Stream<Arguments> invalidPhoneNumbers() {
        return Stream.of(
                Arguments.of("+7902360213", "RU"),
                Arguments.of("+790", "RU"),
                Arguments.of("7902360213", "RU"),
                Arguments.of("8902360213", "RU"),

                Arguments.of("+7 902 360-21-3", "RU"),
                Arguments.of("7 (9023) 60-21", "RU"),
                Arguments.of("8 902 (360)-2", "RU"),

                Arguments.of("+7 902 360 21", "RU"),
                Arguments.of("7 902 360 2", "RU"),
                Arguments.of("8 902 36021", "RU"),

                Arguments.of("invalid number", "RU"),
                Arguments.of(null, "RU"),
                Arguments.of("", "RU")
        );
    }

    private static Stream<Arguments> phoneNumbersNotExistedCountryCode() {
        return Stream.of(
                Arguments.of("79023602131", "RUS"),
                Arguments.of("79023602131", "RUSSIA"),
                Arguments.of("89023602131", "XX"),
                Arguments.of("89023602131", "123")
        );
    }

    private static Stream<Arguments> phoneNumbersWithDifferentFormatting() {
        return Stream.of(
                Arguments.of("7 (9023) 60-21-31", "RU", "+79023602131"),
                Arguments.of("8 902 (360)-21-31", "RU", "+79023602131"),
                Arguments.of("+7 902 360 21 31", "RU", "+79023602131"),
                Arguments.of("8 800 555 35 35", "RU", "+78005553535"),
                Arguments.of("+77123456789", "KZ", "+77123456789"),
                Arguments.of("+971 501-23-45-67", "AE", "+971501234567"),
                Arguments.of("050 1234567", "AE", "+971501234567")
        );
    }

    private static Stream<Arguments> phoneNumbersTypeDetection() {
        return Stream.of(
                Arguments.of("+79023602131", "MOBILE"),
                Arguments.of("8 800 555 35 35", "TOLL_FREE"),
                Arguments.of("8123456789", "FIXED_LINE"),
                Arguments.of("8 916 123 45 67", "MOBILE"),
                Arguments.of("8 495 789 45 61", "FIXED_LINE")
        );
    }

    private static Stream<Arguments> invalidPhoneNumbersErrorReason() {
        return Stream.of(
                Arguments.of("+7902360213", "RU", PhoneNumberParseErrorType.TOO_SHORT),
                Arguments.of("+790236021311", "RU", PhoneNumberParseErrorType.INVALID_LENGTH),
                Arguments.of("+79023602131", "XX", PhoneNumberParseErrorType.INCOMPATIBLE_WITH_COUNTRY_CODE)
        );
    }
}