package io.foodtechlab.i18n.qualifier;


import io.foodtechlab.i18n.I18NTranslator;
import io.foodtechlab.i18n.exception.DefaultTranslatorNotSetException;
import io.foodtechlab.i18n.exception.NotUniqueTranslatorException;
import io.foodtechlab.i18n.exception.TranslatorHasInvalidLocaleException;
import io.foodtechlab.i18n.exception.TranslatorListAreEmptyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Билдер позволяет сконфигурировать определителя переводчика
 *
 * @author kiryanovvi
 * @see I18NTranslatorQualifier
 * @since 1.2
 */
public class I18NTranslatorQualifierBuilder {
    private final List<I18NTranslator> translators = new ArrayList<I18NTranslator>();
    private I18NTranslator defaultTranslator;

    /**
     * Создаёт свой экземпляр класса I18NTranslatorQualifier
     *
     * @return экземпляр класса
     * @since 1.2
     */
    public static I18NTranslatorQualifierBuilder init() {
        return new I18NTranslatorQualifierBuilder();
    }

    /**
     * Метод валидации передаваемых переводчиков.
     *
     * @param translator переводчик которые проверяется
     * @since 1.2
     */
    private void validate(I18NTranslator translator) {
        if (translator.getLocale() == null)
            throw new TranslatorHasInvalidLocaleException();
    }

    /**
     * Метод по формированию списка переводчиков для определителя
     *
     * @param translator I18NTranslator который нужно добавить в список переводчиков
     * @return этот же экземпляр
     * @throws NotUniqueTranslatorException        I18NTranslator с этим уникальным кодом уже добавлен к списку
     * @throws TranslatorHasInvalidLocaleException у переводчика нет {@code UniqueLocaleCode}
     * @since 1.2
     */
    public I18NTranslatorQualifierBuilder add(I18NTranslator translator) {
        validate(translator);
        if (translators.stream().anyMatch(addedTranslator -> addedTranslator.getLocale().equals(translator.getLocale())))
            throw new NotUniqueTranslatorException();

        translators.add(translator);

        return this;
    }

    /**
     * Устанавливает переводчик по умолчанию.
     * Это обязательный параметр для определителя переводчика.
     *
     * @param translator переводчик
     * @return этот же экземпляр
     * @throws TranslatorHasInvalidLocaleException у I18NTranslator отсутствует {@code UniqueLocaleCode}
     * @since 1.2
     */
    public I18NTranslatorQualifierBuilder setDefault(I18NTranslator translator) {
        validate(translator);

        defaultTranslator = translator;

        return this;
    }

    /**
     * Метод, который создаёт экземпляр определителя переводчика.
     * Прежде чем его выполнять, проверьте что вы указали стандартный переводчик методом {@code setDefault}.
     * Так же стоит проверить что вы передали хотя бы один экземпляр переводчика в список переводчиков
     *
     * @return экземпляр определителя переводчика
     * @throws TranslatorListAreEmptyException  не передан не один экземпляр переводчика в список переводчиков
     * @throws DefaultTranslatorNotSetException не указан стандартный переводчик
     * @since 1.2
     */
    public I18NTranslatorQualifier build() {
        if (translators.isEmpty())
            throw new TranslatorListAreEmptyException();
        if (defaultTranslator == null)
            throw new DefaultTranslatorNotSetException();

        return new I18NTranslatorQualifier(translators, defaultTranslator);
    }
}
