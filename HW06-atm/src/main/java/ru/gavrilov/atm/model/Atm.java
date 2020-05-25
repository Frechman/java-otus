package ru.gavrilov.atm.model;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public interface Atm {

    void withdraw(long amount);

    void deposit(List<Banknote> banknotes);

    void balance();
}
