package io.foodtechlab.translator;

import org.springframework.context.MessageSource;
import io.foodtechlab.I18NTranslator;

/**
 * Базовый класс переводчика.<br>
 * <p>
 * Имеет методы настроенные на работу с файлами локализации типа {@code **_localization.properties}.
 * Так же реализует формат ключей инъекций в формате {@code {code}}.
 * По умолчанию берёт значение по ключу {@code CODE.text} из файла локализации
 *
 * @author kiryanovvi
 * @since 1.2
 */
public abstract class LocalizationI18NTranslator implements I18NTranslator {
    /**
     * Источник локализации
     */
    private final MessageSource localizationSource;

    public LocalizationI18NTranslator(MessageSource localizationSource) {
        this.localizationSource = localizationSource;
    }

    /**
     * Берёт значение из файла локализации по ключу CODE.text
     *
     * @param code код по которому можно найти перевод
     * @return текст локализации
     * @since 1.2
     */
    protected String getText(String code) {
        return getProperty(code + ".text");
    }

    /**
     * Приводит key к формату {key}. Этот ключ в дальнейшем используется для поиска инъекций в строку.
     *
     * @param key ключ которому нужно изменить формат
     * @return ключ в изменённом формате
     * @since 1.2
     */
    protected String toInjectionKey(String key) {
        return String.format("{%s}", key);
    }

    /**
     * Находит строку с локализированным текстом из файла **_localization.properties, по переданному ключу и локали указанной в переводчике
     *
     * @param code код по которому можно найти перевод
     * @return локализированный текст
     * @since 1.2
     */
    protected String getProperty(String code) {
        try {
            return localizationSource.getMessage(code, null, getLocale());
        } catch (Exception e) {
            return null;
        }
    }
}
