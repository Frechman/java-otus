package ru.gavrilov.atm.memento;

/**
 * @author gavrilov-sv
 * created on 05.06.2020
 */
public interface Memento {

    /**
     * Restore state of an originator
     */
    void restore();

}
