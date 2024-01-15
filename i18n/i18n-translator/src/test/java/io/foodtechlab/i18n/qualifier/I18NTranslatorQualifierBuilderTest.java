package io.foodtechlab.qualifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.foodtechlab.I18NTranslator;
import io.foodtechlab.core.ExtendedLocale;
import io.foodtechlab.exception.DefaultTranslatorNotSetException;
import io.foodtechlab.exception.NotUniqueTranslatorException;
import io.foodtechlab.exception.TranslatorHasInvalidLocaleException;
import io.foodtechlab.exception.TranslatorListAreEmptyException;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class I18NTranslatorQualifierBuilderTest {

    @Test
    @DisplayName("Простое создание определителя переводчика")
    public void createTest() {
        Successful_TestLocalizationI18NTranslator testTranslator = new Successful_TestLocalizationI18NTranslator();
        Locale locale = new Locale("tt", "TT");

        I18NTranslatorQualifier i18NTranslatorQualifier = I18NTranslatorQualifierBuilder
                .init()
                .add(testTranslator)
                .setDefault(testTranslator)
                .build();

        //Добавился заданный нами определитель
        assertEquals(locale, i18NTranslatorQualifier.get(locale).getLocale());
        assertEquals("Sir Elton Hercules John", i18NTranslatorQualifier.get(locale).translate("ELTON_JOHN"));

        //Добавился стандартный определитель
        assertEquals(locale, i18NTranslatorQualifier.get(ExtendedLocale.RUSSIA).getLocale());
        assertEquals("Sir Elton Hercules John", i18NTranslatorQualifier.get(ExtendedLocale.RUSSIA).translate("ELTON_JOHN"));
    }

    @Test
    @DisplayName("Исключение: если в момент создания не задан переводчик по умолчанию")
    public void whenDefaultTranslatorNotSetExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(DefaultTranslatorNotSetException.class, () -> {
            I18NTranslatorQualifierBuilder
                    .init()
                    .add(new Successful_TestLocalizationI18NTranslator())
                    .build();
        });

        String expectedMessage = "Default translator not set";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Исключение: если в момент создания не задан ни один переводчик")
    public void whenTranslatorListAreEmptyExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(TranslatorListAreEmptyException.class, () -> {
            I18NTranslatorQualifierBuilder
                    .init()
                    .setDefault(new Successful_TestLocalizationI18NTranslator())
                    .build();
        });

        String expectedMessage = "Translator list are empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Исключение: если в список мы передали не уникальный переводчик")
    public void whenNotUniqueTranslatorExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(NotUniqueTranslatorException.class, () -> {
            Successful_TestLocalizationI18NTranslator testTranslator = new Successful_TestLocalizationI18NTranslator();
            I18NTranslatorQualifierBuilder
                    .init()
                    .add(testTranslator)
                    .add(testTranslator)
                    .setDefault(testTranslator)
                    .build();
        });

        String expectedMessage = "Translator passed is not unique. You tried to add a translator twice with the same locale";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Исключение: если в список мы передали переводчик с пустой локалью")
    public void whenTranslatorHasInvalidLocaleExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(TranslatorHasInvalidLocaleException.class, () -> {
            Successful_TestLocalizationI18NTranslator testTranslator = new Successful_TestLocalizationI18NTranslator();
            I18NTranslatorQualifierBuilder
                    .init()
                    .add(testTranslator)
                    .add(new Invalid_TestLocalizationI18NTranslator())
                    .setDefault(testTranslator)
                    .build();
        });

        String expectedMessage = "Locale in this translator is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    protected static class Successful_TestLocalizationI18NTranslator implements I18NTranslator {
        @Override
        public boolean isRightTranslator(Locale locale) {
            return locale.getLanguage().equals("tt");
        }

        @Override
        public Locale getLocale() {
            return new Locale("tt", "TT");
        }

        @Override
        public String translate(String code) {
            return "Sir Elton Hercules John";
        }

        @Override
        public <T> String translate(String code, Map<String, T> injections) {
            return "Sir Elton Hercules John";
        }
    }

    protected static class Invalid_TestLocalizationI18NTranslator implements I18NTranslator {
        @Override
        public boolean isRightTranslator(Locale locale) {
            return locale.getLanguage().equals("tt");
        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public String translate(String code) {
            return "Sir Elton Hercules John";
        }

        @Override
        public <T> String translate(String code, Map<String, T> injections) {
            return "Sir Elton Hercules John";
        }
    }
}
