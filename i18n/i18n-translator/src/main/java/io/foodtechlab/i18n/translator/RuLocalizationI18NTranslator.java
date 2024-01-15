package io.foodtechlab.translator;

import lombok.Getter;
import org.springframework.context.MessageSource;
import io.foodtechlab.core.ExtendedLocale;
import io.foodtechlab.exception.IllegalInjectionTypeException;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Клас переводчика на русский язык.<br>
 * <br>
 * Имеет методы настроенные на работу с файлами локализации типа {@code **_localization.properties}.
 * Так же реализует формат ключей инъекций в формате {@code {code}}.
 * По умолчанию берёт значение по ключу {@code CODE.text} из файла локализации.<br>
 * <br>
 * Имеет поддержку падежей в зависимости от переданного числа.
 * Например, если передать следующие числа можно получать соответствующие склонения:<br>
 * 21 - Осталось 11 рубль<br>
 * 22 - Осталось 11 рубля<br>
 * 25 - Осталось 11 рублей<br>
 * <br>
 * Для того чтобы этот механизм сработал нужно указать в конфигурации<br>
 * <code><br>
 * CODE.text=Осталось {balance} {balance:case}<br>
 * CODE.hasNumberCase=true<br>
 * CODE.caseFields=balance<br>
 * CODE.balance=рубль,рубля,рублей<br>
 * <br>
 * </code>
 * ,где balance - название переменой которую нужно встроить (injections), {**:case} конструкция которая указывает куда нужно встроить склоняемое слово<br>
 * переменная {@code hasNumberCase} это флаг, что нужно учитывать склонение в зависимости от числа<br>
 * переменная {@code caseFields} это перечисление полей в которые нужно встроить переменные со склонениями, переменные разделяются(,)<br>
 * переменная {@code balance} это перечисление вариантов склонения слова, в качестве ключа используется название переменной, варианты разделяются(,)<br>
 *
 * @author kiryanovvi
 * @since 1.2
 */
public class RuLocalizationI18NTranslator extends LocalizationI18NTranslator {

    @Getter
    private final Locale locale = ExtendedLocale.RUSSIA;

    public RuLocalizationI18NTranslator(MessageSource localizationSource) {
        super(localizationSource);
    }

    @Override
    public boolean isRightTranslator(Locale locale) {
        if (locale == null)
            return false;

        return "ru".equals(locale.getLanguage());
    }

    @Override
    public String translate(String code) {
        return getText(code);
    }

    @Override
    public <T> String translate(String code, Map<String, T> injections) {
        String result = getText(code);

        if (hasNumberCase(code)) {
            List<String> caseFields = getCaseFields(code);
            for (Map.Entry<String, T> entry : injections.entrySet()) {
                if (caseFields.contains(entry.getKey()))
                    result = result.replace(toCaseKey(entry.getKey()), getCaseByNumber(code, entry.getKey(), entry.getValue().toString()));

                result = result.replace(toInjectionKey(entry.getKey()), entry.getValue().toString());
            }

        } else
            for (Map.Entry<String, T> entry : injections.entrySet())
                result = result.replace(toInjectionKey(entry.getKey()), entry.getValue().toString());

        return result;
    }

    /**
     * Возвращает склонение в зависимости от переданного числа.
     * Поиск производится по ключу и коду
     *
     * @param code  код перевода
     * @param key   ключ инъекции
     * @param value значение для определения склонения. Обязательно целочисленное число
     * @return склонение в зависимости от переданного числа
     * @throws IllegalInjectionTypeException если переданное value не является целочисленным
     * @since 1.2
     */
    private String getCaseByNumber(String code, String key, String value) {
        long number;

        try {
            number = abs(Long.parseLong(value));
        } catch (NumberFormatException e) {
            throw new IllegalInjectionTypeException(key);
        }

        List<String> caseVariations = getCaseVariation(code, key);
        if (caseVariations.size() < 3)
            return "";

        if (number % 10 == 1 && number % 100 != 11)
            return caseVariations.get(0);
        else if (number % 10 >= 2 && number % 10 <= 4 && (number % 100 < 10 || number % 100 >= 20))
            return caseVariations.get(1);
        else
            return caseVariations.get(2);
    }

    /**
     * Возвращает списком варианты склонения
     *
     * @param code код перевода
     * @param key  ключ инъекции
     * @return список вариантов склонения
     * @since 1.2
     */
    private List<String> getCaseVariation(String code, String key) {
        String caseVariation = getProperty(String.format("%s.%s", code, key));
        if (caseVariation == null)
            return Collections.emptyList();

        return Arrays.asList(caseVariation.split(","));
    }

    /**
     * Приводит название ключа к формату инъекции склонения {:case}
     *
     * @param key ключ инъекции
     * @return название ключа в формате инъекции склонения
     * @since 1.2
     */
    private String toCaseKey(String key) {
        return String.format("{%s:case}", key);
    }

    /**
     * Возвращает список переменных в которые требуется инъекция склонений
     *
     * @param code код перевода
     * @return список переменных в которые требуется инъекция склонений
     * @since 1.2
     */
    private List<String> getCaseFields(String code) {
        return Arrays.asList(getProperty(code + ".caseFields").split(","));
    }


    /**
     * Имеет зависимость между числом и падежом слова.
     * <p>
     * Например: 1 рубль, 2 рубля, 5 рублей
     *
     * @param code код перевода
     * @return true если перевод имеет склонение
     * @since 1.2
     */
    protected boolean hasNumberCase(String code) {
        return "true".equals(getProperty(code + ".hasNumberCase"));
    }

    @Override
    public String getText(String code) {
        return getProperty(code + ".text");
    }
}
