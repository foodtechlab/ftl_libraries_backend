package io.foodtechlab.common.core.utils;

/**
 * Исправленный класс, который проверяет строку на то что она пустая, нулевая или содержит пробелы
 * Кандидат на перенос в rcore
 */
public class StringUtils {

    /**
     * Проверяет что заданный текст не {@code null}, что длина строки больше 0
     * и что строка содержит хотя бы один символ отличный от пустой строки.
     *
     * @param text строка, которую требуется проверить
     * @return флаг того что строка имеет текст
     */
    public static boolean hasText(String text) {
        return text == null || text.isBlank();
    }
}
