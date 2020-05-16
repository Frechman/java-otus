package ru.gavrilov.atm;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public interface Atm {

    void withdraw(long amount);

    void deposit(List<Long> amounts);

    void balance();
}
