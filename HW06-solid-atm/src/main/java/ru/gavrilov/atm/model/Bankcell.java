package ru.gavrilov.atm.model;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public interface Bankcell {

    Nominal nominal();

    void put(Banknote banknote);

    Banknote take();

    long count();
}
