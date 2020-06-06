package ru.gavrilov.atm.api;

import ru.gavrilov.atm.memento.AtmMemento;
import ru.gavrilov.atm.memento.Originator;
import ru.gavrilov.atm.observer.Observer;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public interface Atm extends Originator<AtmMemento>, Observer {

    void withdraw(long amount);

    void deposit(List<Banknote> banknotes);

    void balance();

    String getCode();
}
