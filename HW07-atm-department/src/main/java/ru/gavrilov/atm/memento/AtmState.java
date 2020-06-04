package ru.gavrilov.atm.memento;

import ru.gavrilov.atm.model.AtmCell;
import ru.gavrilov.atm.model.AtmCellImpl;
import ru.gavrilov.atm.model.Banknote;
import ru.gavrilov.atm.model.BanknoteImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavrilov-sv
 * created on 03.06.2020
 */
public class AtmState {

    private List<AtmCell> cells;

    public AtmState(AtmState atmState) {
        copyState(atmState.getCells());
    }

    public AtmState(List<AtmCell> cells) {
        copyState(cells);
    }

    private void copyState(List<AtmCell> cells) {
        this.cells = new ArrayList<>();
        cells.forEach(c -> this.cells.add(new AtmCellImpl(c.nominal(), c.count())));
    }

    public List<AtmCell> getCells() {
        return cells;
    }
}
