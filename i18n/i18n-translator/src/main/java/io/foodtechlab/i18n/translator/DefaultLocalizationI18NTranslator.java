package io.foodtechlab.i18n.translator;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;

/**
 * Стандартный класс переводчик <br>
 * <br>
 * Используется в том случае, если у языка нет региональных особенностей или не известно какая локаль будет использована.
 * Поддерживает возвращение текста по коду перевода и ключу {@code .text}.
 * Поддерживает инъекции переменных в перевод
 *
 * @author kiryanovvi
 * @since 1.2
 */
public class DefaultLocalizationI18NTranslator extends LocalizationI18NTranslator {
    public DefaultLocalizationI18NTranslator(MessageSource localizationSource) {
        super(localizationSource);
    }

    @Override
    public boolean isRightTranslator(Locale locale) {
        return Locale.ENGLISH.getLanguage().equals(locale.getLanguage());
    }

    @Override
    public Locale getLocale() {
        return Locale.ENGLISH;
    }

    @Override
    public String translate(String code) {
        return getText(code);
    }

    @Override
    public <T> String translate(String code, Map<String, T> injections) {
        String result = getText(code);

        for (Map.Entry<String, T> entry : injections.entrySet())
            result = result.replace(toInjectionKey(entry.getKey()), entry.getValue().toString());

        return result;
    }
}
