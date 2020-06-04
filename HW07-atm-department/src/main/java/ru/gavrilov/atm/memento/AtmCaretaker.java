package ru.gavrilov.atm.memento;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public class AtmCaretaker {
    private final Deque<AtmMemento> snapshots = new ArrayDeque<>();

    public void addMemento(AtmMemento memento){
        snapshots.push(memento);
    }

    public AtmMemento get() {
        return snapshots.pop();
    }
}
