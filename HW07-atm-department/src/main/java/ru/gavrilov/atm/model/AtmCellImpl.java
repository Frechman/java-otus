package ru.gavrilov.atm.model;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public class AtmCellImpl implements AtmCell {

    private Nominal nominal;
    private long count;

    public AtmCellImpl(Nominal nominal, long count) {
        this.nominal = nominal;
        this.count = count;
    }

    @Override
    public void put(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            if (banknote.nominal() != nominal) {
                System.out.println("Banknote with nominal " + banknote.nominal().value() + " not allowed.");
                continue;
            }
            count++;
        }
    }

    @Override
    public Banknote take() {
        count--;
        return new BanknoteImpl(nominal);
    }

    @Override
    public long count() {
        return count;
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
