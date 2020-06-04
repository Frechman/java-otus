package ru.gavrilov.atm.model;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public class AtmCellImpl implements AtmCell {

    private Nominal nominal;
    private Deque<Banknote> money;

    public AtmCellImpl(Nominal nominal, Collection<Banknote> money) {
        this.nominal = nominal;
        this.money = new ArrayDeque<>(money);
    }

    @Override
    public void put(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            if (banknote.nominal() != nominal) {
                System.out.println("Banknote with nominal " + banknote.nominal().value() + " not allowed.");
                continue;
            }
            money.push(banknote);
        }
    }

    @Override
    public Banknote take() {
        return money.pop();
    }

    @Override
    public long count() {
        return money.size();
    }

    @Override
    public Nominal nominal() {
        return nominal;
    }

    @Override
    public String toString() {
        return nominal().name() + "(" + count() + ")";
    }
}
