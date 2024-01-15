package io.foodtechlab.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import io.foodtechlab.I18NTranslator;
import io.foodtechlab.core.ExtendedLocale;
import io.foodtechlab.exception.IllegalInjectionTypeException;
import io.foodtechlab.qualifier.I18NTranslatorQualifier;
import io.foodtechlab.qualifier.I18NTranslatorQualifierBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RuLocalizationI18NTranslatorTest {
    I18NTranslatorQualifier i18NTranslatorQualifier;
    MessageSource localizationSource = Mockito.mock(MessageSource.class);

    @BeforeEach
    public void init() {
        //Просто текст
        Mockito.when(localizationSource.getMessage("EMPTY.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Корзина пуста");

        //Текст с падежами
        Mockito.when(localizationSource.getMessage("PRICE.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("До бесплатной доставки осталось {price} {price:case}");
        Mockito.when(localizationSource.getMessage("PRICE.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("PRICE.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("price");
        Mockito.when(localizationSource.getMessage("PRICE.price", null, ExtendedLocale.RUSSIA))
                .thenReturn("рубль,рубля,рублей");

        //Текст с инъекциями, но без падежей и с hasNumberCase= false
        Mockito.when(localizationSource.getMessage("COUNT.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Кол-во товаров: {count}");
        Mockito.when(localizationSource.getMessage("COUNT.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("false");

        //Текст с инъекциями, но без падежей и без hasNumberCase
        Mockito.when(localizationSource.getMessage("ORDER_NUMBER.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Номер заказа: {orderNumber}");

        //Несколько инъекций и повторяющиеся инъекции
        Mockito.when(localizationSource.getMessage("ORDER.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Кол-во блюд: {dishCount}; Из них напитков: {drinkCount}; Платных блюд: {dishCount}");

        //Инъекция строки
        Mockito.when(localizationSource.getMessage("NAME.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Привет, моё имя {name}");

        //Несколько инъекций со склонениями
        Mockito.when(localizationSource.getMessage("ORDER_PRICE.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Номер заказа {orderNumber}. Стоимость заказа {price} {price:case}. Гость должен заплатить {cost} {cost:case}");
        Mockito.when(localizationSource.getMessage("ORDER_PRICE.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("ORDER_PRICE.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("price,cost");
        Mockito.when(localizationSource.getMessage("ORDER_PRICE.price", null, ExtendedLocale.RUSSIA))
                .thenReturn("рубль,рубля,рублей");
        Mockito.when(localizationSource.getMessage("ORDER_PRICE.cost", null, ExtendedLocale.RUSSIA))
                .thenReturn("рубль,рубля,рублей");

        //Забыл указать в тексте ключ с инъекцией склонения
        Mockito.when(localizationSource.getMessage("SHORT_ORDER_PRICE.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Стоимость заказа {price}");
        Mockito.when(localizationSource.getMessage("SHORT_ORDER_PRICE.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("SHORT_ORDER_PRICE.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("price");
        Mockito.when(localizationSource.getMessage("SHORT_ORDER_PRICE.price", null, ExtendedLocale.RUSSIA))
                .thenReturn("рубль,рубля,рублей");

        //Лишние ключи для склонений
        Mockito.when(localizationSource.getMessage("SET_COUNT.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Кол-во ролов в сете: {count} {count:case}");
        Mockito.when(localizationSource.getMessage("SET_COUNT.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("SET_COUNT.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("count,amount");
        Mockito.when(localizationSource.getMessage("SET_COUNT.count", null, ExtendedLocale.RUSSIA))
                .thenReturn("штука,штуки,штук");

        //Разные типы данных
        Mockito.when(localizationSource.getMessage("PROFILE.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Имя: {name}; Возраст: {age} {age:case}");
        Mockito.when(localizationSource.getMessage("PROFILE.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("PROFILE.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("age");
        Mockito.when(localizationSource.getMessage("PROFILE.age", null, ExtendedLocale.RUSSIA))
                .thenReturn("год,года,лет");

        //В конфигурации не указаны варианты склонения
        Mockito.when(localizationSource.getMessage("BIRTH.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("Нашей компании уже {year} {year:case}");
        Mockito.when(localizationSource.getMessage("BIRTH.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("BIRTH.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("year");

        //В конфигурации не указаны не все варианты склонения
        Mockito.when(localizationSource.getMessage("BONUS.text", null, ExtendedLocale.RUSSIA))
                .thenReturn("У вас на балансе {bonus} {bonus:case}");
        Mockito.when(localizationSource.getMessage("BONUS.hasNumberCase", null, ExtendedLocale.RUSSIA))
                .thenReturn("true");
        Mockito.when(localizationSource.getMessage("BONUS.caseFields", null, ExtendedLocale.RUSSIA))
                .thenReturn("bonus");
        Mockito.when(localizationSource.getMessage("BONUS.bonus", null, ExtendedLocale.RUSSIA))
                .thenReturn("икринка,икринки");


        I18NTranslator russianTranslator = new RuLocalizationI18NTranslator(localizationSource);

        i18NTranslatorQualifier = I18NTranslatorQualifierBuilder
                .init()
                .add(russianTranslator)
                .setDefault(russianTranslator)
                .build();
    }

    @Test
    @DisplayName("Переводчик возвращает текст")
    public void simpleTest() {
        assertEquals("Корзина пуста", getTranslate("EMPTY"));
    }

    /**
     * Переводчик возвращает все падежи <br>
     * <br>
     * если в качестве значения передать
     * 21 - ... осталось 21 рубль
     * 22 - ... осталось 22 рубля
     * 25 - ... осталось 22 рублей
     */
    @Test
    @DisplayName("Переводчик возвращает все падежи")
    public void numberCaseTest() {
        assertEquals("До бесплатной доставки осталось 0 рублей", getTranslate("PRICE", Collections.singletonMap("price", 0)));
        assertEquals("До бесплатной доставки осталось 1 рубль", getTranslate("PRICE", Collections.singletonMap("price", 1)));
        assertEquals("До бесплатной доставки осталось 3 рубля", getTranslate("PRICE", Collections.singletonMap("price", 3)));
        assertEquals("До бесплатной доставки осталось 7 рублей", getTranslate("PRICE", Collections.singletonMap("price", 7)));
        assertEquals("До бесплатной доставки осталось 10 рублей", getTranslate("PRICE", Collections.singletonMap("price", 10)));
        assertEquals("До бесплатной доставки осталось 11 рублей", getTranslate("PRICE", Collections.singletonMap("price", 11)));
        assertEquals("До бесплатной доставки осталось 12 рублей", getTranslate("PRICE", Collections.singletonMap("price", 12)));
        assertEquals("До бесплатной доставки осталось 21 рубль", getTranslate("PRICE", Collections.singletonMap("price", 21)));
        assertEquals("До бесплатной доставки осталось 22 рубля", getTranslate("PRICE", Collections.singletonMap("price", 22)));
        assertEquals("До бесплатной доставки осталось 25 рублей", getTranslate("PRICE", Collections.singletonMap("price", 25)));
    }

    @Test
    @DisplayName("Переводчик возвращает текст с инъекциями, но без падежей и с hasNumberCase= false")
    public void withoutCaseTest() {
        assertEquals("Кол-во товаров: 17", getTranslate("COUNT", Collections.singletonMap("count", 17)));
        assertEquals("Кол-во товаров: 2", getTranslate("COUNT", Collections.singletonMap("count", 2)));
    }

    @Test
    @DisplayName("Текст с инъекциями, но без падежей и без hasNumberCase")
    public void withoutCaseIgnoreHasNumberCaseTest() {
        assertEquals("Номер заказа: 2874", getTranslate("ORDER_NUMBER", Collections.singletonMap("orderNumber", 2874)));
        assertEquals("Номер заказа: 777", getTranslate("ORDER_NUMBER", Collections.singletonMap("orderNumber", 777)));
    }

    @Test
    @DisplayName("Несколько инъекций и повторяющиеся инъекции")
    public void severalInjectionsTest() {
        Map<String, Integer> injections = new HashMap<>();
        injections.put("dishCount", 10);
        injections.put("drinkCount", 5);

        assertEquals("Кол-во блюд: 10; Из них напитков: 5; Платных блюд: 10", getTranslate("ORDER", injections));
    }

    @Test
    @DisplayName("Несколько инъекций и инъекция со склонением")
    public void severalInjectionsWithCaseTest() {
        Map<String, Integer> injections = new HashMap<>();
        injections.put("price", 21);
        injections.put("cost", 11);
        injections.put("orderNumber", 1457);

        assertEquals("Номер заказа 1457. Стоимость заказа 21 рубль. Гость должен заплатить 11 рублей", getTranslate("ORDER_PRICE", injections));
    }

    @Test
    @DisplayName("Текст со строковыми инъекциями")
    public void stringInjectionTest() {
        assertEquals("Привет, моё имя Влад", getTranslate("NAME", Collections.singletonMap("name", "Влад")));
        assertEquals("Привет, моё имя Маша", getTranslate("NAME", Collections.singletonMap("name", "Маша")));
        assertEquals("Привет, моё имя Саша", getTranslate("NAME", Collections.singletonMap("name", "Саша")));
    }

    @Test
    @DisplayName("Ошибка в конфигурации с падежами вместо числа передали строку")
    public void illegalConfigurationStringTest() {
        Exception exception = assertThrows(IllegalInjectionTypeException.class, () -> {
            assertEquals("До бесплатной доставки осталось 21 рубль", getTranslate("PRICE", Collections.singletonMap("price", "Влад")));
        });

        String expectedMessage = "In the localization, the field price has the parameter hasNumberCase set to true. For such a configuration, it is necessary to pass injections in integer type";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Ошибка в конфигурации с падежами вместо целочисленного числа передали число с плавающей запятой")
    public void illegalConfigurationFloatTest() {
        Exception exception = assertThrows(IllegalInjectionTypeException.class, () -> {
            assertEquals("До бесплатной доставки осталось 21 рубль", getTranslate("PRICE", Collections.singletonMap("price", 1.50)));
        });

        String expectedMessage = "In the localization, the field price has the parameter hasNumberCase set to true. For such a configuration, it is necessary to pass injections in integer type";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Не указал в тексте ключ с инъекцией склонения")
    public void withoutInjectionCaseKeyTest() {
        assertEquals("Стоимость заказа 178", getTranslate("SHORT_ORDER_PRICE", Collections.singletonMap("price", 178)));
    }

    @Test
    @DisplayName("Лишние ключи для склонений")
    public void tooManyInjectionCaseKeyTest() {
        assertEquals("Кол-во ролов в сете: 26 штук", getTranslate("SET_COUNT", Collections.singletonMap("count", 26)));
    }

    @Test
    @DisplayName("В конфигурации не указаны не все варианты склонения")
    public void notEnoughCaseVariantsTest() {
        assertEquals("У вас на балансе 26 ", getTranslate("BONUS", Collections.singletonMap("bonus", 26)));
    }

    @Test
    @DisplayName("В конфигурации не указаны не все варианты склонения")
    public void emptyCaseVariantsTest() {
        assertEquals("Нашей компании уже 10 ", getTranslate("BIRTH", Collections.singletonMap("year", 10)));
    }

    @Test
    @DisplayName("Разные типы данных")
    public void differentTypeTest() {
        Map<String, String> injections = new HashMap<>();
        injections.put("name", "Владос");
        injections.put("age", "25");

        assertEquals("Имя: Владос; Возраст: 25 лет", getTranslate("PROFILE", injections));
    }

    private <V> String getTranslate(String code, Map<String, V> injections) {
        return i18NTranslatorQualifier.get(ExtendedLocale.RUSSIA).translate(code, injections);
    }


    private String getTranslate(String code) {
        return i18NTranslatorQualifier.get(ExtendedLocale.RUSSIA).translate(code);
    }
}
