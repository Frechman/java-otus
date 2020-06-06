package ru.gavrilov.atm.api;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public interface AtmCell {

    Nominal nominal();

    void put(List<Banknote> banknotes);

    Banknote take();

    long count();
}
