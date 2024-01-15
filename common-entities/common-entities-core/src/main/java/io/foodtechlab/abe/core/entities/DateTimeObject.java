package io.foodtechlab.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.Date;

/**
 * Базовая сущность. Разделенные значения времени c возможностью индексации по отдельным значениям и агрегированию
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DateTimeObject {

    /**
     * Год
     */
    Integer year;

    /**
     * Месяц (январь - 1, декабрь - 12)
     */
    Integer month;

    /**
     * День в месяце
     */
    Integer day;

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
     * День недели
     */
    DayOfWeek dayOfWeek;

    /**
     * Неделя с начала года
     */
    Integer week;

    /**
     * Время в UTC
     */
    Instant instant;

    /**
     * TimeZone, в которой работаем
     */
    String timeZone;


    public static DateTimeObject of(Instant instant, ZoneId zone) {
        int year = instant.atZone(zone).getYear();
        int month = instant.atZone(zone).getMonth().getValue();
        int dayOfMonth = instant.atZone(zone).getDayOfMonth();
        int hour = instant.atZone(zone).getHour();
        int minute = instant.atZone(zone).getMinute();
        int second = instant.atZone(zone).getSecond();
        DayOfWeek dayOfWeek = instant.atZone(zone).getDayOfWeek();
        int weekOfYear = instant.atZone(zone).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        String timeZone = zone.getId();

        return DateTimeObject.builder()
                .year(year)
                .month(month)
                .day(dayOfMonth)
                .hour(hour)
                .minute(minute)
                .dayOfWeek(dayOfWeek)
                .week(weekOfYear)
                .second(second)
                .instant(instant)
                .timeZone(timeZone)
                .build();
    }

    public static DateTimeObject of(ZonedDateTime zonedDateTime) {
        return DateTimeObject.of(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    public static DateTimeObject of(Date date, ZoneId zone) {
        return DateTimeObject.of(date.toInstant(), zone);
    }

    public static DateTimeObject of(Long mills, ZoneId zone) {
        return DateTimeObject.of(Instant.ofEpochMilli(mills), zone);
    }

    public static ZonedDateTime getZonedDateTime(DateTimeObject dateTimeObject) {
        return dateTimeObject.instant.atZone(ZoneId.of(dateTimeObject.timeZone));
    }

    public ZonedDateTime toZonedDateTime() {
        return DateTimeObject.getZonedDateTime(this);
    }
}
