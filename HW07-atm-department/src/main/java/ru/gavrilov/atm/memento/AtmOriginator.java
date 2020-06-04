package ru.gavrilov.atm.memento;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public interface AtmOriginator {

    AtmMemento saveState();

    void restoreState(AtmMemento memento);
}
