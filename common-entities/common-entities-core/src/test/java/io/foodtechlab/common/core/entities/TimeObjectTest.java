package io.foodtechlab.common.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты объекта {@link TimeObject}
 */
public class TimeObjectTest {
    /**
     * Тест проверяет создание {@link TimeObject} из объекта {@link LocalTime}
     *
     * @param localTime объект со временем
     */
    @ParameterizedTest
    @DisplayName("Создание из LocalTime")
    @MethodSource("createLocalTimeArguments")
    public void byLocalTime(LocalTime localTime) {
        TimeObject timeObject = TimeObject.of(localTime);

        assertNotNull(timeObject);
        assertEquals(localTime.getHour(), timeObject.getHour());
        assertEquals(localTime.getMinute(), timeObject.getMinute());
        assertEquals(localTime.getSecond(), timeObject.getSecond());
        assertEquals(localTime.getNano(), timeObject.getNano());

        // Проверка форматированного времени
        String formattedTime = String.format("%1$tH:%1$tM:%1$tS.%1$tL", localTime);
        assertNotNull(timeObject.getFormattedLocalDateTime());
        assertEquals(formattedTime, timeObject.getFormattedLocalDateTime().getValueInString());
    }

    /**
     * Проверяем создание с помощью Builder
     *
     * @param timeParams список параметров с времени (часы, минуты, секунды, наносекунды, список минимум из двух полей
     *                   и максимум из четырёх
     */
    @ParameterizedTest
    @DisplayName("Создание из Builder")
    @MethodSource("byBuilderArguments")
    public void byBuilderTest(List<Integer> timeParams) {
        if (timeParams == null || timeParams.size() < 2 || timeParams.size() > 4)
            throw new IllegalArgumentException("The parameters were not passed correctly. Parameters are required. The number of parameters must be greater than or equal to 2 and less than or equal to 4.");

        TimeObject timeObject = createTimeObjectByBuilder(timeParams);
        LocalTime localTime = createLocalTimeByBuilder(timeParams);

        assertNotNull(timeObject);
        assertEquals(timeParams.get(0), timeObject.getHour());
        assertEquals(timeParams.get(1), timeObject.getMinute());

        //Значения если переданы секунды и наносекунды соответственно
        if (timeParams.size() >= 3)
            assertEquals(timeParams.get(2), timeObject.getSecond());
        if (timeParams.size() == 4)
            assertEquals(timeParams.get(3), timeObject.getNano());

        //Значения если секунды и наносекунды не переданы
        if (timeParams.size() < 3) {
            assertEquals(0, timeObject.getSecond());
            assertEquals(0, timeObject.getNano());
        }
        if (timeParams.size() < 4)
            assertEquals(0, timeObject.getNano());

        // Проверка форматированного времени
        String formattedTime = String.format("%1$tH:%1$tM:%1$tS.%1$tL", localTime);
        assertNotNull(timeObject.getFormattedLocalDateTime());
        assertEquals(formattedTime, timeObject.getFormattedLocalDateTime().getValueInString());
    }

    /**
     * @return набор аргументов для теста создания с помощью билдера
     */
    static Stream<Arguments> byBuilderArguments() {
        return Stream.of(
                Arguments.of(List.of(12, 59)),
                Arguments.of(List.of(1, 1)),
                Arguments.of(List.of(22, 22)),
                Arguments.of(List.of(23, 59)),
                Arguments.of(List.of(0, 0)),
                Arguments.of(List.of(12, 14, 58)),
                Arguments.of(List.of(2, 2, 2)),
                Arguments.of(List.of(2, 2, 2, 2)),
                Arguments.of(List.of(11, 10, 9, 12222222))
        );
    }

    /**
     * @param timeParams список параметров с времени (часы, минуты, секунды, наносекунды,
     *                   список минимум из двух полей и максимум из четырёх
     * @return наш объект времени
     */
    private TimeObject createTimeObjectByBuilder(List<Integer> timeParams) {
        if (timeParams == null || timeParams.size() < 2 || timeParams.size() > 4)
            throw new IllegalArgumentException("The parameters were not passed correctly. Parameters are required. The number of parameters must be greater than or equal to 2 and less than or equal to 4.");

        switch (timeParams.size()) {
            case 2:
                return TimeObject
                        .builder()
                        .hour(timeParams.get(0))
                        .minute(timeParams.get(1))
                        .build();
            case 3:
                return TimeObject
                        .builder()
                        .hour(timeParams.get(0))
                        .minute(timeParams.get(1))
                        .second(timeParams.get(2))
                        .build();
            case 4:
                return TimeObject
                        .builder()
                        .hour(timeParams.get(0))
                        .minute(timeParams.get(1))
                        .second(timeParams.get(2))
                        .nanoOfSecond(timeParams.get(3))
                        .build();
            default:
                return TimeObject.ofNanoOfDay(0L);
        }
    }

    /**
     * Создаём LocalTime из списка параметров
     *
     * @param timeParams список параметров с времени (часы, минуты, секунды, наносекунды, список минимум из двух полей
     *                   и максимум из четырёх
     * @return локальное время
     */
    private LocalTime createLocalTimeByBuilder(List<Integer> timeParams) {
        if (timeParams == null || timeParams.size() < 2 || timeParams.size() > 4)
            throw new IllegalArgumentException("The parameters were not passed correctly. Parameters are required. The number of parameters must be greater than or equal to 2 and less than or equal to 4.");

        switch (timeParams.size()) {
            case 2:
                return LocalTime.of(timeParams.get(0), timeParams.get(1));
            case 3:
                return LocalTime.of(timeParams.get(0), timeParams.get(1), timeParams.get(2));
            case 4:
                return LocalTime.of(timeParams.get(0), timeParams.get(1), timeParams.get(2), timeParams.get(3));
            default:
                return LocalTime.ofNanoOfDay(0);
        }
    }


    /**
     * Проверяем создание с помощью Builder. Передаём не корректные значения и ожидаем ошибки.
     *
     * @param timeParams список параметров с времени (часы, минуты, секунды, наносекунды, список минимум из двух полей
     *                   и максимум из четырёх
     */
    @ParameterizedTest
    @DisplayName("Создание из Builder c параметрами которые вызывают ошибки")
    @MethodSource("byBuilderInvalidArguments")
    public void byBuilderInvalidTest(List<Integer> timeParams) {
        if (timeParams == null || timeParams.size() < 2 || timeParams.size() > 4)
            throw new IllegalArgumentException("The parameters were not passed correctly. Parameters are required. The number of parameters must be greater than or equal to 2 and less than or equal to 4.");

        assertThrows(DateTimeException.class, () -> createTimeObjectByBuilder(timeParams));
    }

    /**
     * @return набор аргументов для теста создания с помощью билдера которые должны вызывать ошибку
     */
    static Stream<Arguments> byBuilderInvalidArguments() {
        return Stream.of(
                Arguments.of(List.of(-12, 99)),
                Arguments.of(List.of(44, -1)),
                Arguments.of(List.of(22, 22, -1)),
                Arguments.of(List.of(23, 59, 999, 12)),
                Arguments.of(List.of(23, 59, 59, -1))
        );
    }

    /**
     * Проверяем создание с помощью Builder. Передаём в качестве null значений часы и минуты.
     * Ожидаем {@link NullPointerException}.
     */
    @Test
    @DisplayName("Создание из Builder hour и minute null")
    public void byBuilderNullTest() {
        assertThrows(NullPointerException.class, () -> TimeObject
                .builder()
                .hour(null)
                .minute(12)
                .build()
        );

        assertThrows(NullPointerException.class, () -> TimeObject
                .builder()
                .hour(12)
                .minute(null)
                .build()
        );

        assertThrows(NullPointerException.class, () -> TimeObject
                .builder()
                .hour(null)
                .minute(null)
                .build()
        );
    }


    /**
     * Тест на создание {@link TimeObject} из строки.
     * Строка должна соответствовать формату времени {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}.
     *
     * @param text строка формате {@link java.time.format.DateTimeFormatter#ISO_LOCAL_TIME}
     */
    @ParameterizedTest
    @DisplayName("Создание из строки")
    @MethodSource("createStringArguments")
    public void byString(String text) {
        TimeObject timeObject = TimeObject.parse(text);
        LocalTime localTime = LocalTime.parse(text);

        assertNotNull(timeObject);
        assertEquals(localTime.getHour(), timeObject.getHour());
        assertEquals(localTime.getMinute(), timeObject.getMinute());
        assertEquals(localTime.getSecond(), timeObject.getSecond());
        assertEquals(localTime.getNano(), timeObject.getNano());

        // Проверка форматированного времени
        String formattedTime = String.format("%1$tH:%1$tM:%1$tS.%1$tL", localTime);
        assertNotNull(timeObject.getFormattedLocalDateTime());
        assertEquals(formattedTime, timeObject.getFormattedLocalDateTime().getValueInString());
    }

    /**
     * @return набор аргументов для теста создания из строки
     */
    static Stream<Arguments> createStringArguments() {
        return Stream.of(
                Arguments.of("01:12:22"), //создания времени из строки
                Arguments.of("12:44:01"),
                Arguments.of("23:23:23"),
                Arguments.of("01:12"),
                Arguments.of("12:44"),
                Arguments.of("23:23")
        );
    }


    /**
     * Тест проверяет что при передаче некорректного формата строки будет
     * выброшено исключение {@link DateTimeParseException}
     *
     * @param text строка с форматом времени
     */
    @ParameterizedTest
    @DisplayName("Некорректный формат времени")
    @MethodSource("byStringInvalidArguments")
    public void byStringInvalidTest(String text) {
        assertThrows(DateTimeParseException.class, () -> TimeObject.parse(text));
    }

    /**
     * @return набор аргументов для теста создания из строки с ошибкой формата даты
     */
    static Stream<Arguments> byStringInvalidArguments() {
        return Stream.of(
                Arguments.of("1:12:22"), //создания времени из строки
                Arguments.of("12:4:01"),
                Arguments.of("23:23:3"),
                Arguments.of("01"),
                Arguments.of("1"),
                Arguments.of("df"),
                Arguments.of("@!"),
                Arguments.of("_12:_12"),
                Arguments.of("24:00:30"),
                Arguments.of("55:00:30"),
                Arguments.of("12:61:30"),
                Arguments.of("12:51:99"),
                Arguments.of("012:051:19")
        );
    }

    /**
     * Тест создания из пустой строки. Ожидаемое поведение, объект вернёт {@code null} объект.
     * Ошибки выдано не будет
     *
     * @param text пустой текст
     */
    @ParameterizedTest
    @DisplayName("Создание из пустой строки")
    @MethodSource("byEmptyStringArguments")
    public void byEmptyString(String text) {
        assertNull(TimeObject.parse(text));
    }

    /**
     * @return набор аргументов для теста создания из пустой строки
     */
    static Stream<Arguments> byEmptyStringArguments() {
        return Stream.of(
                Arguments.of("  "),
                Arguments.of(" "),
                Arguments.of("")
        );
    }

    /**
     * Тест создание из {@code null}
     */
    @Test()
    @DisplayName("Создание из null")
    public void byNullString() {
        assertNull(TimeObject.parse(null));
    }

    /**
     * Тестируем создание объекта из наносекунд от начала дня
     *
     * @param nanoOfDay кол-во наносекунд от начала дня
     */
    @ParameterizedTest
    @DisplayName("Создание из наносекунд от начала дня")
    @MethodSource("createNanoOfDayArguments")
    public void byNanoOfDay(Long nanoOfDay) {
        TimeObject timeObject = TimeObject.ofNanoOfDay(nanoOfDay);
        LocalTime localTime = LocalTime.ofNanoOfDay(nanoOfDay);

        assertNotNull(timeObject);
        assertEquals(localTime.getHour(), timeObject.getHour());
        assertEquals(localTime.getMinute(), timeObject.getMinute());
        assertEquals(localTime.getSecond(), timeObject.getSecond());
        assertEquals(localTime.getNano(), timeObject.getNano());

        // Проверка форматированного времени
        String formattedTime = String.format("%1$tH:%1$tM:%1$tS.%1$tL", localTime);
        assertNotNull(timeObject.getFormattedLocalDateTime());
        assertEquals(formattedTime, timeObject.getFormattedLocalDateTime().getValueInString());
    }

    /**
     * @return аргументы для создания из {@link LocalTime}
     */
    static Stream<Arguments> createLocalTimeArguments() {
        return Stream.of(
                Arguments.of(LocalTime.of(12, 30, 15, 500000000)), // Example time
                Arguments.of(LocalTime.of(10, 20, 30, 0)),
                Arguments.of(LocalTime.of(23, 59, 59, 999999999))
        );
    }

    /**
     * @return аргументы для создания из наносекунд от начала дня
     */
    static Stream<Arguments> createNanoOfDayArguments() {
        return Stream.of(
                Arguments.of(45015500000000L),
                Arguments.of(37215000000000L),
                Arguments.of(86399999999999L)
        );
    }

    /**
     * Тест на создание объекта из null значения {@link LocalTime}
     */
    @Test
    @DisplayName("Создание из null значения для конструктора LocalTime")
    public void byLocalTime_NullInput() {
        assertThrows(NullPointerException.class, () -> TimeObject.of((LocalTime) null));
    }

    /**
     * Тест на создание объекта из отрицательного значения
     */
    @Test
    @DisplayName("Создание из отрицательного значения для конструктора ofNanoOfDay")
    public void byNanoOfDay_NegativeValue() {
        assertThrows(
                java.time.DateTimeException.class,
                () -> TimeObject.ofNanoOfDay(-1L)
        );
    }

    /**
     * Создание объекта из 0
     */
    @Test
    @DisplayName("Создание из 0 значения для конструктора ofNanoOfDay")
    public void byNanoOfDay_ZeroValue() {
        TimeObject timeObject = TimeObject.ofNanoOfDay(0L);
        assertNotNull(timeObject);
        assertEquals(0, timeObject.getHour());
        assertEquals(0, timeObject.getMinute());
        assertEquals(0, timeObject.getSecond());
        assertEquals(0, timeObject.getNano());
    }

    /**
     * Создание объекта из null для конструктора ofNanoOfDay
     */
    @DisplayName("Создание из null значения для конструктора ofNanoOfDay")
    @Test
    public void byNanoOfDay_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> TimeObject.ofNanoOfDay(null)
        );
    }

    /**
     * Проверяем передачу очень большого числа
     */
    @DisplayName("Передача очень большого числа")
    @Test
    public void byNanoOfDay_NormalValue_CheckValueInLong() {
        long nanoOfDay = 45015500000000L; // Примерное значение nanoOfDay
        TimeObject timeObject = TimeObject.ofNanoOfDay(nanoOfDay);
        assertNotNull(timeObject);

        // Рассчитываем ожидаемое значение valueInLong
        String expectedValueInLongString = "123015500"; // Ожидаемое значение для 12:30:15.500000000
        long expectedValueInLong = Long.parseLong(expectedValueInLongString);

        // Проверяем, что значение valueInLong соответствует ожидаемому
        assertEquals(expectedValueInLong, timeObject.getFormattedLocalDateTime().getValueInLong());
    }
}
