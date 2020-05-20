package ru.gavrilov.atm.model;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public class BankCellImpl implements Bankcell {

    private Nominal nominal;
    private Deque<Banknote> money = new ArrayDeque<>();

    public BankCellImpl(Nominal nominal, Collection<Banknote> money) {
        this.nominal = nominal;
        this.money.addAll(money);
    }

    @Override
    public void put(Banknote banknote) {
        if (banknote.nominal() != nominal) {
            throw new IllegalArgumentException("nominal exception");
        }
        money.push(banknote);
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
