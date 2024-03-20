package io.foodtechlab.common.core.types;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

/**
 * Перечисление типов телефонных номеров.
 * <p>
 * Данное перечисление является аналогом {@link PhoneNumberUtil.PhoneNumberType} из библиотеки google libphonenumber.
 * Все значения и описания взяты из этой библиотеки.
 */
public enum PhoneNumberType {
    /**
     * Стационарный телефонный номер.
     */
    FIXED_LINE,
    /**
     * Мобильный телефонный номер.
     */
    MOBILE,
    /**
     * Стационарный или мобильный телефонный номер. В некоторых регионах (например, в США) невозможно определить,
     * является ли номер стационарным или мобильным, просто посмотрев на сам номер.
     */
    FIXED_LINE_OR_MOBILE,
    /**
     * Бесплатный телефонный номер.
     */
    TOLL_FREE,
    /**
     * Платный телефонный номер.
     */
    PREMIUM_RATE,
    /**
     * Стоимость звонка делится между абонентом и получателем и, как правило, ниже, чем у платных номеров.
     * Более подробную информацию можно найти здесь: <a href="https://en.wikipedia.org/wiki/Shared_Cost_Service">Shared Cost Service</a>.
     */
    SHARED_COST,
    /**
     * Номер телефона VoIP. Это включает в себя TSoIP (Telephony Service over IP).
     */
    VOIP,
    /**
     * Персональный номер телефона связан с конкретным человеком и может быть маршрутизирован либо на
     * мобильный, либо на стационарный номер. Более подробную информацию можно найти здесь: <a href="https://en.wikipedia.org/wiki/Personal_Numbers">Personal Numbers</a>.
     */
    PERSONAL_NUMBER,
    /**
     * Пейджер.
     */
    PAGER,
    /**
     * Используется для "Универсальных доступных номеров" или "Номеров компаний". Они могут быть дальше маршрутизированы на
     * конкретные офисы, но позволяют использовать один номер для компании.
     */
    UAN,
    /**
     * Используется для "Номеров доступа к голосовой почте".
     */
    VOICEMAIL,
    /**
     * Тип телефонного номера неизвестен, когда он не соответствует известным шаблонам для конкретного региона.
     */
    UNKNOWN;

    /**
     * Преобразует тип телефонного номера из PhoneNumberUtil.PhoneNumberType в PhoneNumberType.
     *
     * @param phoneNumberType тип телефонного номера из PhoneNumberUtil.PhoneNumberType
     *
     * @return тип телефонного номера в формате PhoneNumberTypeCustom
     */
    public static PhoneNumberType fromPhoneNumberUtilType(PhoneNumberUtil.PhoneNumberType phoneNumberType) {
        for (PhoneNumberType type : PhoneNumberType.values()) {
            if (type.name().equals(phoneNumberType.name())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PhoneNumberUtil.PhoneNumberType: " + phoneNumberType);
    }
}