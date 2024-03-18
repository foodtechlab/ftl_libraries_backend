package io.foodtechlab.common.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.time.format.DateTimeFormatter;
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

    /**
     * Представление даты и времени в локальном часовом поясе
     */
    FormattedLocalDateTime formattedLocalDateTime;

    /**
     * Свойства часового пояса
     */
    TimeZoneProperties timeZoneProperties;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class FormattedLocalDateTime {
        /**
         * Дата и время относительно локального часового пояса.
         * Формируется по маске: YYYYMMDDHHMMSSMMM
         */
        Long valueInLong;
        /**
         * Дата и время относительно локального часового пояса в строковом формате.
         * Формируется по маске: "yyyy-MM-dd'T'HH:mm:ss.SSS"
         */
        String valueInString;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class TimeZoneProperties {
        /**
         * Смещение времени относительно координированного всемирного времени (UTC)
         * Например, "+03:00" для часового пояса Europe/Moscow.
         */
        String offsetFromUTC;
        /**
         * Флаг, указывающий на активность летнего смещения часового пояса для данной временной зоны в момент времени отраженного в исходном объекте
         */
        Boolean isSummerTimeOffset;
    }


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
        FormattedLocalDateTime formattedLocalDateTime = calculateFormattedLocalDateTime(instant, zone);
        TimeZoneProperties timeZoneProperties = calculateTimeZoneProperties(instant, zone);

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
                .formattedLocalDateTime(formattedLocalDateTime)
                .timeZoneProperties(timeZoneProperties)
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

    private static FormattedLocalDateTime calculateFormattedLocalDateTime(Instant instant, ZoneId zone) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String valueInString = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        Long valueInLong = Long.parseLong(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));

        return FormattedLocalDateTime.builder()
                .valueInLong(valueInLong)
                .valueInString(valueInString)
                .build();
    }

    private static TimeZoneProperties calculateTimeZoneProperties(Instant instant, ZoneId zone) {
        String offsetFromUTC = zone.getRules().getOffset(instant).toString();
        boolean isSummerTimeOffset = zone.getRules().isDaylightSavings(instant);
        return TimeZoneProperties.builder()
                .offsetFromUTC(offsetFromUTC)
                .isSummerTimeOffset(isSummerTimeOffset)
                .build();
    }
}