package ru.gavrilov.atm.memento;

import ru.gavrilov.atm.model.Atm;

/**
 * @author gavrilov-sv
 * created on 03.06.2020
 */
public class AtmMemento implements Memento {

    private final Atm atm;
    private final AtmState state;

    public AtmMemento(Atm atm, AtmState state) {
        this.atm = atm;
        this.state = state;
    }

    public AtmState getState() {
        return state;
    }

    public void restore() {
        atm.restoreState(this);
    }
}
