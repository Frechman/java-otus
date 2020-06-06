package ru.gavrilov.atm.memento;

/**
 * @author gavrilov-sv
 * created on 05.06.2020
 * aka snapshot
 */
public interface Memento {

    /**
     * Restore state of an originator
     */
    void restore();

}
