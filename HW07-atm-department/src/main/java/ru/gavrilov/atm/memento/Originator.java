package ru.gavrilov.atm.memento;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public interface Originator<T extends Memento> {

    T saveState();

    void restoreState(T memento);
}
