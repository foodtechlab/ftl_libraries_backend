package io.foodtechlab.common.core.entities;

import com.google.i18n.phonenumbers.NumberParseException;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Код страны.
 * <p>
 * Это перечисление хранит информацию о стране в международном формате
 */
@Getter
@Accessors(fluent = true)
public enum CountryCode {
    // TODO: рассмотреть хранение ISO имени, а не значения ENUM
    //todo рассмотреть запись countryName на языке этой страны
    RUSSIA(7, "RU", "Russian Federation", "+7 ([000]) [000] [00] [00]", 11),
    USA(1, "US", "United States of America", "+1 ([000]) [000]-[0000]", 11),
    CANADA(1, "CA", "Canada", "+1 ([000]) [000]-[0000]", 11),
    UNITED_ARAB_EMIRATES(971, "AE", "United Arab Emirates", "+(971 [00]) [000] [00] [00]", 12);

    //https://dirask.com/posts/Java-list-of-ISO-3166-country-codes-pamYKp
        /*KAZAKHSTAN(7),
        BYELORUSSIAN(7),

        EGYPT(20),
        ALGERIA(21),
        LIBYA(21),
        TUNISIA(21),
        WESTERN_SAHARA(21),
        SOUTH_AFRICA(27),
        GREECE(30),
        NETHERLANDS(31),
        BELGIUM(32),
        FRANCE(33),
        SPAIN(34),
        HUNGARY(36),
        ITALY(39),
        VATICAN_CITY(39),
        ROMANIA(40),
        LIECHTENSTEIN(41),
        SWITZERLAND(41),
        AUSTRIA(43),
        UNITED_KINGDOM(44),
        DENMARK(45),
        SWEDEN(46),
        NORWAY(47),
        POLAND(48),
        GERMANY(49),
        PERU(51),
        CUBA(53),
        ARGENTINA(54),
        BRAZIL(55),
        CHILE(56),
        COLOMBIA(57),
        VENEZUELA(58),
        MALAYSIA(60),
        AUSTRALIA(61),
        EAST_TIMOR(62),
        INDONESIA(62),
        PHILIPPINES(63),
        NEW_ZEALAND(64),
        SINGAPORE(65),
        THAILAND(66),
        JAPAN(81),
        SOUTH_KOREA(82),
        VIETNAM(84),
        CHINA(86),
        TURKEY(90),
        INDIA(91),
        PAKISTAN(92),
        AFGHANISTAN(93),
        SRI_LANKA(94),
        IRAN(98),
        MOROCCO(212),
        GAMBIA(220),
        SENEGAL(221),
        MAURITANIA(222),
        MALI(223),
        GUINEA(224),
        IVORY_COAST(225),
        BURKINA_FASO(226),
        NIGER(227),
        TOGOLESE_REPUBLIC(228),
        BENIN(229),
        MAURITIUS(230),
        LIBERIA(231),
        SIERRA_LEONE(232),
        GHANA(233),
        NIGERIA(234),
        CHAD(235),
        CENTRAL_AFRICAN_REPUBLIC(236),
        CAMEROON(237),
        CAPE_VERDE(238),
        EQUATORIAL_GUINEA(240),
        GABON(241),
        CONGO(242),
        ZAIRE(243),
        ANGOLA(244),
        GUINEABISSAU(245),
        SAINT_HELENA_AND_ASCENSION_ISLAND(247),
        SUDAN(249),
        RWANDESE_REPUBLIC(250),
        ETHIOPIA(251),
        SOMALIA(252),
        DJIBOUTI(253),
        KENYA(254),
        TANZANIA(255),
        UGANDA(256),
        BURUNDI(257),
        MOZAMBIQUE(258),
        ZAMBIA(260),
        MADAGASCAR(261),
        REUNION(262),
        ZIMBABWE(263),
        NAMIBIA(264),
        MALAWI(265),
        LESOTHO(266),
        BOTSWANA(267),
        SWAZILAND(268),
        COMOROS_AND_MAYOTTE_ISLAND(269),
        GREENLAND(299),
        GIBRALTAR(350),
        PORTUGAL(351),
        LUXEMBOURG(352),
        IRISH_REPUBLIC(353),
        ICELAND(354),
        ALBANIA(355),
        MALTA(356),
        CYPRUS(357),
        FINLAND(358),
        BULGARIA(359),
        LITHUANIA(370),
        LATVIA(371),
        ESTONIA(372),
        ARMENIA(374),
        BELARUS(375),
        ANDORRA(376),
        MONACO(377),
        REPUBLIC_OF_SAN_MARINO(378),
        UKRAINE(380),
        YUGOSLAVIA(381),
        CROATIA(385),
        SLOVENIA(386),
        BOSNIA_AND_HERZEGOVINA(387),
        MACEDONIA(389),
        CZECH_REPUBLIC(420),
        SLOVAKIA(421),
        FALKLAND_ISLANDS(500),
        BELIZE(501),
        GUATEMALA(502),
        EL_SALVADOR(503),
        HONDURAS(504),
        NICARAGUA(505),
        COSTA_RICA(506),
        PANAMA(507),
        SAINT_PIERRE_ET_MIQUELON(508),
        HAITI(509),
        FRENCH_ANTILLES(590),
        BOLIVIA(591),
        GUYANA(592),
        ECUADOR(593),
        FRENCH_GUIANA(594),
        PARAGUAY(595),
        MARTINIQUE(596),
        SURINAME(597),
        URUGUAY(598),
        NETHERLANDS_ANTILLES(599),
        NORTHERN_MARIANA_ISLANDS(670),
        CHRISTMAS_ISLAND(672),
        COCOS_ISLANDS(672),
        NORFOLK_ISLAND(672),
        BRUNEI_DARUSALAAM(673),
        NAURU(674),
        PAPUA_NEW_GUINEA(675),
        TONGA(676),
        SOLOMON_ISLANDS(677),
        FIJI(679),
        COOK_ISLANDS(682),
        AMERICAN_SAMOA(684),
        WESTERN_SAMOA(685),
        KIRIBATI_REPUBLIC(686),
        NEW_CALEDONIA(687),
        TUVALU(688),
        FRENCH_POLYNESIA(689),
        MICRONESIA(691),
        MARSHALL_ISLANDS(692),
        NORTH_KOREA(850),
        HONG_KONG(852),
        MACAO(853),
        CAMBODIA(855),
        LAOS(856),
        BANGLADESH(880),
        TAIWAN(886),
        MALDIVES(960),
        LEBANON(961),
        JORDAN(962),
        SYRIA(963),
        IRAQ(964),
        KUWAIT(965),
        SAUDI_ARABIA(966),
        NORTH_YEMEN(967),
        OMAN(968),
        SOUTH_YEMEN(969),

        ISRAEL(972),
        BAHRAIN(973),
        QATAR(974),
        MONGOLIA(976),
        NEPAL(977),
        TADJIKISTAN(992),
        TURKMENISTAN(993),
        AZERBAIJAN(994),
        GEORGIA(995),
        KIRGHIZIA(996),
        UZBEKISTAN(998),
        BAHAMAS(1242),
        BARBADOS(1246),
        ANGUILLA(1264),
        ANTIGUA_AND_BARBUDA(1268),
        BRITISH_VIRGIN_ISLANDS(1284),
        US_VIRGIN_ISLANDS(1340),
        CAYMAN_ISLANDS(1345),
        BERMUDA(1441),
        GRENADA(1473),
        TURKS_CAICOS_ISLANDS(1649),
        COMMONWEALTH_OF_THE_NORTHERN_MARIANA_ISLANDS(1670),
        ST_LUCIA(1758),
        DOMINICA(1767),
        ST_VINCENT_AND_THE_GRENADINES(1784),
        PUERTO_RICO(1787),
        DOMINICAN_REPUBLIC(1809),
        TRINIDAD_AND_TOBAGO(1868),
        ST_KITTS_AND_NEVIS(1869),
        JAMAICA(1876)*/;

    /**
     * Префикс номера телефона в формате числа: 7
     */
    private final Integer prefix;
    /**
     * Префикс номера телефона в формате строки: 7
     */
    private final String prefixString;
    /**
     * Код строны в формате ISO 3166-1 Alpha-2
     */
    private final String countryCode;
    /**
     * Название страны
     */
    private final String countryName;
    /**
     * Маска номера телефона для страны
     */
    private final String phoneMask;
    /**
     * Максимальное кол-во цифр в номере телефона
     */
    private final int phoneLength;

    CountryCode(Integer prefix, String countryCode, String countryName, String phoneMask, int phoneLength) {
        this.prefix = prefix;
        this.prefixString = String.valueOf(prefix);
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.phoneMask = phoneMask;
        this.phoneLength = phoneLength;
    }

    public static CountryCode parse(String phoneNumber) throws NumberParseException {
        String formatted = PhoneNumber.format(phoneNumber);

        for (CountryCode countryCode : CountryCode.values()) {
            if (formatted.startsWith(countryCode.prefixString())) {
                return countryCode;
            }
        }

        throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "CountryCode parse exception");
    }
}
