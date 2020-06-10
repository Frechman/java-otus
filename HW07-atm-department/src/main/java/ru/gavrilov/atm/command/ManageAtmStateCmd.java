package ru.gavrilov.atm.command;

import ru.gavrilov.atm.memento.AtmMemento;
import ru.gavrilov.atm.api.Atm;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author gavrilov-sv
 * created on 05.06.2020
 * <p>
 * A command object can act as a caretaker.
 */
public class ManageAtmStateCmd implements Transactional {

    /**
     * History of the ATM's state
     */
    private final Deque<AtmMemento> snapshots = new ArrayDeque<>();

    private final Atm atm;

    /**
     * @param atm is originator.
     */
    public ManageAtmStateCmd(Atm atm) {
        this.atm = atm;
    }

    /**
     * Save ATM's state
     */
    @Override
    public void execute() {
        snapshots.push(atm.saveState());
    }

    /**
     * Restore ATM's state
     */
    public void undo() {
        snapshots.pop().restore();
    }

    public Atm getAtm() {
        return atm;
    }
}
