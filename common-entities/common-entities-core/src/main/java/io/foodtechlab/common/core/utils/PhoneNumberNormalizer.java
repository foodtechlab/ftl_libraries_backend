package io.foodtechlab.common.core.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
@Deprecated(forRemoval = true)
public class PhoneNumberNormalizer {
    /**
     * Метод для нормализации телефонного номера (удаление всех нецифровых символов).
     * Если передан полный номер (11 символов), заменяет префикс 8 на 7.
     *
     * @param phoneNumber телефонный номер
     *
     * @return нормализованный телефонный номер
     *
     * @deprecated Используйте {@link io.foodtechlab.common.core.entities.PhoneNumber#formatFullRuNumber(String)}
     */
    @Deprecated(forRemoval = true)
    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "";
        }
        String formatted = phoneNumber.replaceAll("[^0-9]", "").trim();

        if (formatted.length() == 11 && formatted.startsWith("89")) {
            formatted = "7" + formatted.substring(1);
        }
        return formatted;
    }
}