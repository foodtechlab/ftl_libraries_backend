package io.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.utils.PhoneNumberUtils;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

/**
 * Enum Country представляет перечисление стран с их кодами и префиксами телефонных номеров.
 * Этот класс предназначен для тех случаев, когда мы пытаемся определить код страны без указания его в isoTwoLetterCountryCode,
 * то есть только по номеру телефона. Так как для некоторых стран (например, Россия и Казахстан) код страны одинаков (7),
 * то необходимо смотреть по префиксу самого номера.
 * <p>
 * Существуют региональные префиксы и префиксы мобильных операторов.
 * Можно заполнять как префикс целиком (например, 904), или только то количество символов, которые позволят однозначно
 * установить принадлежность номера (например, если все номера для префикса вида 9ХХ принадлежат данной стране, то достаточно указать только цифру 9).
 * <p>
 * Ссылка с информацией о префиксах для России:
 * <a href="https://ru.wikipedia.org/wiki/%D0%A2%D0%B5%D0%BB%D0%B5%D1%84%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D0%BF%D0%BB%D0%B0%D0%BD_%D0%BD%D1%83%D0%BC%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D0%B8_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D0%B8">Телефонный план нумерации Российской Федерации</a>
 * <p>
 * Ссылка с информацией о префиксах для Казахстана:
 * <a href="https://ru.wikipedia.org/wiki/%D0%A2%D0%B5%D0%BB%D0%B5%D1%84%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D0%BF%D0%BB%D0%B0%D0%BD_%D0%BD%D1%83%D0%BC%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D0%B8_%D0%9A%D0%B0%D0%B7%D0%B0%D1%85%D1%81%D1%82%D0%B0%D0%BD%D0%B0">Телефонный план нумерации Казахстана</a>
 */
@Getter
public enum Country {
    RUSSIA("RU", Set.of(7, 8), Set.of("3", "4", "5", "8", "9")),
    KAZAKHSTAN("KZ", Set.of(7), Set.of("6", "7")),
    UNITED_ARAB_EMIRATES("AE", Set.of(971), Collections.emptySet());

    private final String isoTwoLetterCountryCode;
    private final Set<Integer> phoneCodes;
    private final Set<String> phonePrefixes;

    Country(String isoTwoLetterCountryCode, Set<Integer> phoneCodes, Set<String> phonePrefixes) {
        this.isoTwoLetterCountryCode = isoTwoLetterCountryCode;
        this.phoneCodes = phoneCodes;
        this.phonePrefixes = phonePrefixes;
    }

    public static String findByPhoneNumber(String phoneNumber) {
        String normalizedPhoneNumber = PhoneNumberUtils.normalizePhoneNumber(phoneNumber);

        for (Country currentCountry : Country.values()) {
            for (Integer countryCallingCode : currentCountry.phoneCodes) {
                String codeAsString = countryCallingCode.toString();
                if (normalizedPhoneNumber.startsWith(codeAsString)) {
                    String numberWithoutCode = normalizedPhoneNumber.substring(codeAsString.length());
                    if (!currentCountry.phonePrefixes.isEmpty() && !currentCountry.phonePrefixes.contains(numberWithoutCode.substring(0, 1))) {
                        continue;
                    }
                    return currentCountry.isoTwoLetterCountryCode;
                }
            }
        }
        return null;
    }
}