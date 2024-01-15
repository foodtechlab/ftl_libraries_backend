package io.foodtechlab.i18n.translator;

import io.foodtechlab.i18n.I18NTranslator;
import io.foodtechlab.i18n.qualifier.I18NTranslatorQualifier;
import io.foodtechlab.i18n.qualifier.I18NTranslatorQualifierBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultLocalizationI18NTranslatorTest {
    I18NTranslatorQualifier i18NTranslatorQualifier;
    MessageSource localizationSource = Mockito.mock(MessageSource.class);

    @BeforeEach
    public void init() {
        //Просто текст
        Mockito.when(localizationSource.getMessage("EMPTY.text", null, Locale.ENGLISH))
                .thenReturn("Empty cart");

        //Инъекция переменных
        Mockito.when(localizationSource.getMessage("ORDER_COST.text", null, Locale.ENGLISH))
                .thenReturn("Order cost is {cost}");

        //Разные типы данных
        Mockito.when(localizationSource.getMessage("PROFILE.text", null, Locale.ENGLISH))
                .thenReturn("Name is {name}; Age is {age} ");


        I18NTranslator defaultTranslator = new DefaultLocalizationI18NTranslator(localizationSource);

        i18NTranslatorQualifier = I18NTranslatorQualifierBuilder
                .init()
                .add(defaultTranslator)
                .setDefault(defaultTranslator)
                .build();
    }

    @Test
    @DisplayName("Переводчик возвращает текст")
    public void simpleTest() {
        assertEquals("Empty cart", getTranslate("EMPTY"));
    }

    @Test
    @DisplayName("Инъекция переменной в текст")
    public void injectionTest() {
        assertEquals("Order cost is 145", getTranslate("ORDER_COST", Collections.singletonMap("cost", 145)));
    }

    @Test
    @DisplayName("Разные типы данных")
    public void differentTypeTest() {
        Map<String, String> injections = new HashMap<>();
        injections.put("name", "Vlados");
        injections.put("age", "25");

        assertEquals("Name is Vlados; Age is 25 ", getTranslate("PROFILE", injections));
    }

    private <V> String getTranslate(String code, Map<String, V> injections) {
        return i18NTranslatorQualifier.get(Locale.ENGLISH).translate(code, injections);
    }

    private String getTranslate(String code) {
        return i18NTranslatorQualifier.get(Locale.ENGLISH).translate(code);
    }
}
