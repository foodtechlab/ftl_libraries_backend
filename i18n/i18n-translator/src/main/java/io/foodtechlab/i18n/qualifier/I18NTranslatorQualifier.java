package io.foodtechlab.qualifier;

import io.foodtechlab.I18NTranslator;

import java.util.List;
import java.util.Locale;


/**
 * Возвращает I18NTranslator в зависимости от переданной локали.
 * Если не найдёт подходящего переводчика, вернёт заданного по умолчанию
 *
 * @author kiryanovvi
 * @see I18NTranslator
 * @since 1.2
 */
public class I18NTranslatorQualifier {
    private final List<I18NTranslator> translators;
    private final I18NTranslator defaultTranslator;

    I18NTranslatorQualifier(List<I18NTranslator> translators, I18NTranslator defaultTranslator) {
        this.translators = translators;
        this.defaultTranslator = defaultTranslator;
    }

    /**
     * Метод поиска I18NTranslator
     *
     * @param locale локаль на которую требуется перевести
     * @return переводчик
     * @see I18NTranslator
     * @since 1.2
     */
    public I18NTranslator get(Locale locale) {
        return translators
                .stream()
                .filter(translator -> translator.isRightTranslator(locale))
                .findAny()
                .orElse(defaultTranslator);
    }
}
