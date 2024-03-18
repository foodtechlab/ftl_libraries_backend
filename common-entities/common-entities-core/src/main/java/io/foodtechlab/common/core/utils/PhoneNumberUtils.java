package io.foodtechlab.common.core.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PhoneNumberUtils {
    /**
     * Метод для нормализации телефонного номера (удаление всех нецифровых символов).
     *
     * @param phoneNumber телефонный номер
     *
     * @return нормализованный телефонный номер
     */
    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        return phoneNumber.replaceAll("\\D", "").trim();
    }
}