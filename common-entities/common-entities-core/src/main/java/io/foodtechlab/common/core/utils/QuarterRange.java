package io.foodtechlab.common.core.utils;

import io.foodtechlab.common.core.types.Quarter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

/**
 * Класс утилита возвращает `LocalDateTime` начала квартала (метод `start`) и `LocalDateTime` окончания квартала (метод `end`)
 */
@UtilityClass
public class QuarterRange {
    /**
     * @param quarter квартал
     * @param year    год
     * @return дата начала квартала
     */
    public LocalDateTime start(Quarter quarter, short year) {
        return LocalDateTime.of(LocalDate.of(year, quarter.getStart(), 1), LocalTime.MIN);
    }

    /**
     * @param quarter квартал
     * @param year    год
     * @return дата начала квартала
     */
    public LocalDateTime end(Quarter quarter, short year) {
        return LocalDateTime.of(LocalDate.of(year, quarter.getEnd(), YearMonth.of(year, quarter.getEnd()).lengthOfMonth()), LocalTime.MAX);
    }
}
