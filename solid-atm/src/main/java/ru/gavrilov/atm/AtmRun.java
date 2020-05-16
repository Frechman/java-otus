package ru.gavrilov.atm;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmRun {

    public static void main(String[] args) {
        Map<Nominal, Long> initCassette = new EnumMap<>(Nominal.class);
        initCassette.put(Nominal.TEN, 10L);
        initCassette.put(Nominal.HUNDRED, 5L);
        initCassette.put(Nominal.THOUSAND, 5L);

        Atm atm = new AtmImpl(initCassette);
        atm.balance();

        atm.deposit(List.of(100L, 100L, 10L, 10L, 5L, 1L, 1000L));
        atm.balance();

        atm.withdraw(926);
        atm.balance();
    }
}
