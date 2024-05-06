package io.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.utils.StringUtils;
import lombok.*;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Базовая сущность. Разделенные значения времени c возможностью индексации по отдельным значениям и агрегированию
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE,
        builderMethodName = "innerBuilder",
        builderClassName = "TimeObjectInnerBuilder")
public class TimeObject {
    /**
     * Часы
     */
    private Integer hour;
    /**
     * Минуты
     */
    private Integer minute;
    /**
     * Секунды
     */
    private Integer second;
    /**
     * Наносекунды
     */
    private Integer nano;
    /**
     * Представление времени
     */
    private FormattedLocalTime formattedLocalTime;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder(access = AccessLevel.PRIVATE,
            builderMethodName = "innerBuilder",
            builderClassName = "FormattedTimeInnerBuilder")
    public static class FormattedLocalTime {
        /**
         * Время по маске: HHmmssSSS
         */
        private Long valueInLong;
        /**
         * Время по маске: "HH:mm:ss.SSS"
         */
        private String valueInString;
        /**
         * Время в наносекундах от начала дня.
         */
        private Long valueInNanoOfDay;
    }

    /**
     * Метод создаёт TimeObject из переданной строки, например {@code 14:12}.
     * <p>
     * Строка должна быть корректным временем и будет создана используя {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
     * <p>
     * Если будет передана пустая строка, то вернётся null объект.
     *
     * @param text строка со временем
     * @return собранный из строки {@link TimeObject}
     * @throws DateTimeParseException вернёт ошибку если не будет распознан
     */
    public static TimeObject parse(String text) {
        if (StringUtils.hasText(text))
            return null;

        return TimeObject.of(LocalTime.parse(text));
    }

    /**
     * Защищённый статический фабричный метод, который служит основой для билдера.
     * В нём происходят основные валидации билдера, а так же инициализация всех полей.
     *
     * @param hour         час, принимает значение от 0 до 23
     * @param minute       минута, принимает значение от 0 до 59
     * @param second       секунда, принимает значение от 0 до 59
     * @param nanoOfSecond наносекунда, принимает значение 999,999,999
     * @return экземпляр {@link TimeObject}
     * @throws NullPointerException если {@code hour} и {@code minute} будут переданы как null
     * @throws DateTimeException    если параметры не будет соответствовать своим цифровым диапазонам
     */
    @Builder
    protected static TimeObject of(@NonNull Integer hour, @NonNull Integer minute, Integer second, Integer nanoOfSecond) {
        second = second == null ? 0 : second;
        nanoOfSecond = nanoOfSecond == null ? 0 : nanoOfSecond;

        return TimeObject.of(LocalTime.of(hour, minute, second, nanoOfSecond));
    }

    /**
     * Фабричный метод, который создаёт экземпляр класса {@link TimeObject}.
     * На вход мы принимаем стандартный класс времени {@link LocalTime}.
     *
     * @param localTime локальное время
     * @return экземпляр класса {@link TimeObject}
     * @throws NullPointerException будет возвращено в случае, если аргумент
     *                              {@code localTime} будет null.
     */
    public static TimeObject of(@NonNull LocalTime localTime) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        int nano = localTime.getNano();
        FormattedLocalTime formatted = buildFormattedTime(localTime);

        return TimeObject.innerBuilder()
                .hour(hour)
                .minute(minute)
                .second(second)
                .nano(nano)
                .formattedLocalTime(formatted)
                .build();
    }

    /**
     * Фабричный метод создаёт экземпляр класса {@link TimeObject},
     * используя в качестве параметров время от начала дня в наносекундах.
     *
     * @param nanoOfDay наносекунды от начала дня
     * @return экземпляр класса {@link TimeObject}
     * @throws DateTimeException будет выброшено, если передать отрицательное значение
     */
    public static TimeObject ofNanoOfDay(Long nanoOfDay) {
        LocalTime localTime = LocalTime.ofNanoOfDay(nanoOfDay);
        return TimeObject.of(localTime);
    }

    /**
     * Приватный метод который создаёт {@link FormattedLocalTime}.
     * В методе задаётся форматирование полей этого внутреннего объекта
     *
     * @param localTime локальное время
     * @return объект с отформатированным локальным временем
     */
    private static FormattedLocalTime buildFormattedTime(LocalTime localTime) {
        String valueInString = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        Long valueInLong = Long.parseLong(localTime.format(DateTimeFormatter.ofPattern("HHmmssSSS")));
        Long valueInNanoOfDay = localTime.toNanoOfDay();

        return FormattedLocalTime.innerBuilder()
                .valueInLong(valueInLong)
                .valueInString(valueInString)
                .valueInNanoOfDay(valueInNanoOfDay)
                .build();
    }

    /**
     * Возвращает указанное время в формате {@code String}, например {@code 10:15}.
     * <p>
     * Результатом будет один из ISO-8601 форматов:
     * <ul>
     * <li>{@code HH:mm}</li>
     * <li>{@code HH:mm:ss}</li>
     * <li>{@code HH:mm:ss.SSS}</li>
     * <li>{@code HH:mm:ss.SSSSSS}</li>
     * <li>{@code HH:mm:ss.SSSSSSSSS}</li>
     * </ul>
     * Формат будет упрощаться пропуская нулевые значения.
     *
     * @return строка, которая представляет время, не пустая
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(18);
        int hourValue = hour;
        int minuteValue = minute;
        int secondValue = second;
        int nanoValue = nano;
        buf.append(hourValue < 10 ? "0" : "").append(hourValue)
                .append(minuteValue < 10 ? ":0" : ":").append(minuteValue);
        if (secondValue > 0 || nanoValue > 0) {
            buf.append(secondValue < 10 ? ":0" : ":").append(secondValue);
            if (nanoValue > 0) {
                buf.append('.');
                if (nanoValue % 1000_000 == 0) {
                    buf.append(Integer.toString((nanoValue / 1000_000) + 1000).substring(1));
                } else if (nanoValue % 1000 == 0) {
                    buf.append(Integer.toString((nanoValue / 1000) + 1000_000).substring(1));
                } else {
                    buf.append(Integer.toString((nanoValue) + 1000_000_000).substring(1));
                }
            }
        }
        return buf.toString();
    }
}

