package ru.gavrilov.atm.memento;


/**
 * @author gavrilov-sv
 * created on 03.06.2020
 *
 *
 */
public class AtmMemento {

    private final AtmState state;

    public AtmMemento(AtmState state) {
        this.state = new AtmState(state);
    }

    public AtmState getState() {
        return state;
    }
}
