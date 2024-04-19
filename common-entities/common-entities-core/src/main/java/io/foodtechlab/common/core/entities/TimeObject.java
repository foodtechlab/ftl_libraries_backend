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
@NoArgsConstructor
@SuperBuilder
@Getter
public class TimeObject {
    /**
     * Часы
     */
    Integer hour;

    /**
     * Минуты
     */
    Integer minute;

    /**
     * Секунды
     */
    Integer second;

    /**
     * Наносекунды
     */
    Integer nano;

    /**
     * Представление времени
     */
    FormattedLocalTime formattedLocalDateTime;

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Getter
    public static class FormattedLocalTime {
        /**
         * Время по маске: HHMMSSMMM
         */
        Long valueInLong;
        /**
         * Время по маске: "HH:mm:ss.SSS"
         */
        String valueInString;
        /**
         * Время в наносекундах c начала дня.
         */
        Long valueInNanoOfDay;
    }


    public static TimeObject of(LocalTime localTime) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        int nano = localTime.getNano();
        FormattedLocalTime formattedLocalTime = calculateFormattedLocalDateTime(localTime);

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

    private static FormattedLocalTime calculateFormattedLocalDateTime(LocalTime localTime) {
        String valueInString = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS"));
        Long valueInLong = Long.parseLong(localTime.format(DateTimeFormatter.ofPattern("HHmmssSSSSSSSSS")));
        Long valueInNanoOfDay = localTime.toNanoOfDay();

        return FormattedLocalTime.builder()
                .valueInLong(valueInLong)
                .valueInString(valueInString)
                .valueInNanoOfDay(valueInNanoOfDay)
                .build();
    }
}