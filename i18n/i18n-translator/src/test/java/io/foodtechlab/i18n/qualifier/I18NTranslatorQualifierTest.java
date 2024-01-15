package io.foodtechlab.qualifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.foodtechlab.I18NTranslator;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18NTranslatorQualifierTest {
    private final static Locale testLocale = new Locale("tt", "TT");
    private final static Locale defaultLocale = new Locale("dd", "DD");
    private static final String testTranslation = "Sir Elton Hercules John";
    private static final String defaultTranslation = "Lauren Eve Mayberry";
    I18NTranslatorQualifier i18NTranslatorQualifier;

    @BeforeEach
    public void init() {
        i18NTranslatorQualifier = I18NTranslatorQualifierBuilder
                .init()
                .add(new Successful_TestLocalizationI18NTranslator())
                .setDefault(new Successful_DefaultLocalizationI18NTranslator())
                .build();
    }

    @Test
    @DisplayName("По тестовой локали найдётся необходимый переводчик и вернёт переведённую фразу")
    public void getTestTranslator() {
        I18NTranslator translator = i18NTranslatorQualifier.get(testLocale);

        assertEquals(testLocale, translator.getLocale());
        assertEquals(testTranslation, translator.translate("CODE"));
    }

    @Test
    @DisplayName("По заданной локали не найдётся переводчика и вернётся тот что задан по умолчанию")
    public void getDefaultTranslator() {
        I18NTranslator translator = i18NTranslatorQualifier.get(Locale.ENGLISH);

        assertEquals(defaultLocale, translator.getLocale());
        assertEquals(defaultTranslation, translator.translate("CODE"));
    }

    protected static class Successful_TestLocalizationI18NTranslator implements I18NTranslator {
        @Override
        public boolean isRightTranslator(Locale locale) {
            return locale.getLanguage().equals("tt");
        }

        @Override
        public Locale getLocale() {
            return testLocale;
        }

        @Override
        public String translate(String code) {
            return testTranslation;
        }

        @Override
        public <T> String translate(String code, Map<String, T> injections) {
            return testTranslation;
        }
    }

    protected static class Successful_DefaultLocalizationI18NTranslator implements I18NTranslator {
        @Override
        public boolean isRightTranslator(Locale locale) {
            return locale.getLanguage().equals("dd");
        }

        @Override
        public Locale getLocale() {
            return defaultLocale;
        }

        @Override
        public String translate(String code) {
            return defaultTranslation;
        }

        @Override
        public <T> String translate(String code, Map<String, T> injections) {
            return defaultTranslation;
        }
    }
}
