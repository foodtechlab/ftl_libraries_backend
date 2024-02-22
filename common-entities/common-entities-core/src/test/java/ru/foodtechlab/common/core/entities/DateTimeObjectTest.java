package ru.foodtechlab.common.core.entities;

import io.foodtechlab.common.core.entities.DateTimeObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестирование класса DateTimeObject на основе различных вариантов создания объекта.
 */
public class DateTimeObjectTest {

    /**
     * Проверяет корректность создания объекта DateTimeObject на основе Instant и ZoneId.
     *
     * @param instant Instant для создания объекта DateTimeObject.
     * @param zoneId  ZoneId для создания объекта DateTimeObject.
     */
    @ParameterizedTest
    @MethodSource("createInstantArguments")
    public void createDateTimeObject(Instant instant, ZoneId zoneId) {
        DateTimeObject dateTimeObject = DateTimeObject.of(instant, zoneId);

        // Проверка соответствия полей созданного объекта DateTimeObject и ожидаемых значений.
        assertEquals(instant.atZone(zoneId).getYear(), dateTimeObject.getYear());
        assertEquals(instant.atZone(zoneId).getMonth().getValue(), dateTimeObject.getMonth());
        assertEquals(instant.atZone(zoneId).getDayOfMonth(), dateTimeObject.getDay());
        assertEquals(instant.atZone(zoneId).getHour(), dateTimeObject.getHour());
        assertEquals(instant.atZone(zoneId).getMinute(), dateTimeObject.getMinute());
        assertEquals(instant.atZone(zoneId).getSecond(), dateTimeObject.getSecond());
        assertEquals(instant.atZone(zoneId).getDayOfWeek(), dateTimeObject.getDayOfWeek());
        assertEquals(instant.atZone(zoneId).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), dateTimeObject.getWeek());
        assertEquals(instant, dateTimeObject.getInstant());
        assertEquals(zoneId.getId(), dateTimeObject.getTimeZone());
        assertEquals(instant.atZone(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")),
                dateTimeObject.getFormattedLocalDateTime().getValueInString());
        assertEquals(Long.parseLong(instant.atZone(zoneId).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))),
                dateTimeObject.getFormattedLocalDateTime().getValueInLong());
        assertEquals(zoneId.getRules().getOffset(instant).toString(),
                dateTimeObject.getTimeZoneProperties().getOffsetFromUTC());
        assertEquals(zoneId.getRules().isDaylightSavings(instant),
                dateTimeObject.getTimeZoneProperties().getIsSummerTimeOffset());
    }

    /**
     * Предоставляет аргументы для теста createDateTimeObject().
     *
     * @return Stream с аргументами для создания объекта DateTimeObject на основе Instant и ZoneId.
     */
    static Stream<Arguments> createInstantArguments() {
        return Stream.of(
                Arguments.of(Instant.now(), ZoneId.systemDefault()),
                Arguments.of(Instant.now(), ZoneId.of("UTC")),
                Arguments.of(Instant.parse("1998-02-04T12:00:00Z"), ZoneId.of("Europe/Volgograd")),
                Arguments.of(Instant.parse("1998-07-04T12:00:00Z"), ZoneId.of("Europe/Volgograd")),
                Arguments.of(Instant.parse("1998-02-04T12:00:00Z"), ZoneId.of("Asia/Krasnoyarsk")),
                Arguments.of(Instant.parse("1998-07-04T12:00:00Z"), ZoneId.of("Asia/Krasnoyarsk"))
        );
    }
}