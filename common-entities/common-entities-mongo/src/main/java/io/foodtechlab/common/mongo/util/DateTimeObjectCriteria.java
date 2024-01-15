package io.foodtechlab.common.mongo.util;

import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DateTimeObjectCriteria {

    public static WithField withField(String field) {
        return new WithField(field);
    }

    public static class WithField {
        private final String field;

        private WithField(String field) {
            this.field = field;
        }

        private static List<Criteria> dateList(String field, LocalDate from, LocalDate to) {
            List<Criteria> criteria = new ArrayList<Criteria>(3);

            criteria.add(Criteria.where(field + ".year").gte(from.getYear()).lte(to.getYear()));
            criteria.add(Criteria.where(field + ".month").gte(from.getMonthValue()).lte(to.getMonthValue()));
            criteria.add(Criteria.where(field + ".day").gte(from.getDayOfMonth()).lte(to.getDayOfMonth()));

            return criteria;
        }

        private static List<Criteria> timeList(String field, LocalTime from, LocalTime to) {
            List<Criteria> criteria = new ArrayList<Criteria>(3);

            criteria.add(Criteria.where(field + ".hour").gte(from.getHour()).lte(to.getHour()));
            criteria.add(Criteria.where(field + ".minute").gte(from.getMinute()).lte(to.getMinute()));
            criteria.add(Criteria.where(field + ".second").gte(from.getSecond()).lte(to.getSecond()));

            return criteria;
        }

        /**
         * Метод вызывает ошибки.
         * Не может построить критерии для запросов где один из параметров ниже другого.
         * Например 2021.09.01-2022.08.01.
         * Из-за того что месяц в дате за 2021 год больше чем в 2022 результатом такого запроса будет: 0
         */
        @Deprecated
        public Optional<Criteria> range(LocalDateTime from, LocalDateTime to) {
            if (from == null || to == null)
                return Optional.empty();

            List<Criteria> criteria = new ArrayList<Criteria>(7);

            criteria.addAll(dateList(field, from.toLocalDate(), to.toLocalDate()));
            criteria.addAll(timeList(field, from.toLocalTime(), to.toLocalTime()));

            return Optional.of(new Criteria().andOperator(criteria.toArray(Criteria[]::new)));
        }

        /**
         * Метод вызывает ошибки.
         * Не может построить критерии для запросов где один из параметров ниже другого.
         * Например 13:54-14:00.
         * Из-за того что минута во времени за 13 час больше чем за 14 результатом такого запроса будет: 0
         */
        @Deprecated
        public Optional<Criteria> range(LocalTime from, LocalTime to) {
            if (from == null || to == null)
                return Optional.empty();

            return Optional.of(new Criteria().andOperator(timeList(field, from, to).toArray(Criteria[]::new)));
        }

        /**
         * Метод вызывает ошибки.
         * Не может построить критерии для запросов где один из параметров ниже другого.
         * Например 2021.09.01-2022.08.01.
         * Из-за того что месяц в дате за 2021 год больше чем в 2022 результатом такого запроса будет: 0
         */
        @Deprecated
        public Optional<Criteria> range(LocalDate from, LocalDate to) {
            if (from == null || to == null)
                return Optional.empty();

            return Optional.of(new Criteria().andOperator(dateList(field, from, to).toArray(Criteria[]::new)));
        }
    }


}
