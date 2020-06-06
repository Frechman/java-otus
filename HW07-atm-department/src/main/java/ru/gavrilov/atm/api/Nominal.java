package ru.gavrilov.atm.api;

import java.util.Optional;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public enum Nominal {

    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    THOUSAND(1000);

    private long value;

    Nominal(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static Optional<Nominal> getByValue(long value) {
        for (Nominal nominal : values()) {
            if (nominal.value == value) {
                return Optional.of(nominal);
            }
        }
        return Optional.empty();
    }
}
