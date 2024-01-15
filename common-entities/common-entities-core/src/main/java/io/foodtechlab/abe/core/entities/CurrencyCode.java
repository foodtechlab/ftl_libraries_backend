package io.foodtechlab.core.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <a href="https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4%D1%8B_%D0%B8_%D0%BA%D0%BB%D0%B0%D1%81%D1%81%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%82%D0%BE%D1%80%D1%8B_%D0%B2%D0%B0%D0%BB%D1%8E%D1%82">...</a>
 * <p>
 * Классификация валют. ISO 4217 alpha-3
 */
@RequiredArgsConstructor
@Getter
public enum CurrencyCode {
    EUR("€"),
    USD("$"),
    RUB("₽"),
    AED("aed");

    /**
     * Обозначение валют
     */
    private final String symbol;
}
