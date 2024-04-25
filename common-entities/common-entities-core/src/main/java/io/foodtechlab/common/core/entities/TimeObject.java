package io.foodtechlab.common.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Базовая сущность. Разделенные значения времени c возможностью индексации по отдельным значениям и агрегированию
 */
@AllArgsConstructor
@SuperBuilder
@Getter
@NoArgsConstructor
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
    private FormattedLocalTime formattedLocalDateTime;

    @AllArgsConstructor
    @SuperBuilder
    @NoArgsConstructor
    @Getter
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


    public static TimeObject of(LocalTime localTime) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        int nano = localTime.getNano();
        FormattedLocalTime formattedLocalTime = createFormattedLocalDateTime(localTime);

        return TimeObject.builder()
                .hour(hour)
                .minute(minute)
                .second(second)
                .nano(nano)
                .formattedLocalDateTime(formattedLocalTime)
                .build();
    }

    public static TimeObject ofNanoOfDay(Long nanoOfDay) {
        LocalTime localTime = LocalTime.ofNanoOfDay(nanoOfDay);
        return TimeObject.of(localTime);
    }

    private static FormattedLocalTime createFormattedLocalDateTime(LocalTime localTime) {
        String valueInString = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        Long valueInLong = Long.parseLong(localTime.format(DateTimeFormatter.ofPattern("HHmmssSSS")));
        Long valueInNanoOfDay = localTime.toNanoOfDay();

        return FormattedLocalTime.builder()
                .valueInLong(valueInLong)
                .valueInString(valueInString)
                .valueInNanoOfDay(valueInNanoOfDay)
                .build();
    }
}
