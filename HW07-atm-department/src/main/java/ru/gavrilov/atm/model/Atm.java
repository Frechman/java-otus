package ru.gavrilov.atm.model;

import ru.gavrilov.atm.memento.AtmOriginator;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public interface Atm extends AtmOriginator {

    void withdraw(long amount);

    void deposit(List<Banknote> banknotes);

    void balance();
}
