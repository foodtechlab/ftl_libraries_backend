package io.foodtechlab.core.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Month;

@Getter
@RequiredArgsConstructor
public enum Quarter {
    Q1(Month.JANUARY, Month.MARCH),
    Q2(Month.APRIL, Month.JUNE),
    Q3(Month.JULY, Month.SEPTEMBER),
    Q4(Month.OCTOBER, Month.DECEMBER);

    private final Month start;
    private final Month end;
}
