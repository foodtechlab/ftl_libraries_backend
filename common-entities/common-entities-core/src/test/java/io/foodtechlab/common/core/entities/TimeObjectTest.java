package io.foodtechlab.common.core.entities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TimeObjectTest {

    @ParameterizedTest
    @MethodSource("createLocalTimeArguments")
    public void createFromLocalTime(LocalTime localTime) {
        TimeObject timeObject = TimeObject.of(localTime);

        assertNotNull(timeObject);
        assertEquals(localTime.getHour(), timeObject.getHour());
        assertEquals(localTime.getMinute(), timeObject.getMinute());
        assertEquals(localTime.getSecond(), timeObject.getSecond());
        assertEquals(localTime.getNano(), timeObject.getNano());

        // Проверка форматированного времени
        String formattedTime = String.format("%02d:%02d:%02d.%09d",
                localTime.getHour(), localTime.getMinute(), localTime.getSecond(), localTime.getNano());
        assertEquals(formattedTime, timeObject.getFormattedLocalDateTime().getValueInString());
    }

    @ParameterizedTest
    @MethodSource("createNanoOfDayArguments")
    public void createFromNanoOfDay(Long nanoOfDay) {
        TimeObject timeObject = TimeObject.ofNanoOfDay(nanoOfDay);
        LocalTime localTime = LocalTime.ofNanoOfDay(nanoOfDay);

        assertNotNull(timeObject);
        assertEquals(localTime.getHour(), timeObject.getHour());
        assertEquals(localTime.getMinute(), timeObject.getMinute());
        assertEquals(localTime.getSecond(), timeObject.getSecond());
        assertEquals(localTime.getNano(), timeObject.getNano());

        // Проверка форматированного времени
        String formattedTime = String.format("%02d:%02d:%02d.%09d",
                localTime.getHour(), localTime.getMinute(), localTime.getSecond(), localTime.getNano());
        assertEquals(formattedTime, timeObject.getFormattedLocalDateTime().getValueInString());
    }

    static Stream<Arguments> createLocalTimeArguments() {
        return Stream.of(
                Arguments.of(LocalTime.of(12, 30, 15, 500000000)), // Example time
                Arguments.of(LocalTime.of(10, 20, 30, 0)),
                Arguments.of(LocalTime.of(23, 59, 59, 999999999))
        );
    }

    static Stream<Arguments> createNanoOfDayArguments() {
        return Stream.of(
                Arguments.of(45015500000000L), // Example nano of day
                Arguments.of(37215000000000L),
                Arguments.of(86399999999999L)
        );
    }

    @Test
    public void createFromLocalTime_NullInput() {
        assertThrows(NullPointerException.class, () -> TimeObject.of((LocalTime) null));
    }

    @Test
    public void createFromLocalTime_NegativeValues() {
        assertThrows(
                java.time.DateTimeException.class,
                () -> TimeObject.of(LocalTime.of(-1, 30, 15, 500000000))
        );
    }

    @Test
    public void createFromNanoOfDay_NegativeValue() {
        assertThrows(
                java.time.DateTimeException.class,
                () -> TimeObject.ofNanoOfDay(-1L)
        );
    }

    @Test
    public void createFromNanoOfDay_ZeroValue() {
        TimeObject timeObject = TimeObject.ofNanoOfDay(0L);
        assertNotNull(timeObject);
        assertEquals(0, timeObject.getHour());
        assertEquals(0, timeObject.getMinute());
        assertEquals(0, timeObject.getSecond());
        assertEquals(0, timeObject.getNano());
    }

    @Test
    public void createFromNanoOfDay_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> TimeObject.ofNanoOfDay(null)
        );
    }

    @Test
    public void createFromNanoOfDay_NormalValue_CheckValueInLong() {
        long nanoOfDay = 45015500000000L; // Примерное значение nanoOfDay
        TimeObject timeObject = TimeObject.ofNanoOfDay(nanoOfDay);
        assertNotNull(timeObject);

        // Расчитываем ожидаемое значение valueInLong
        String expectedValueInLongString = "123015500000000"; // Ожидаемое значение для 12:30:15.500000000
        long expectedValueInLong = Long.parseLong(expectedValueInLongString);

        // Проверяем, что значение valueInLong соответствует ожидаемому
        assertEquals(expectedValueInLong, timeObject.getFormattedLocalDateTime().getValueInLong());
    }
}
