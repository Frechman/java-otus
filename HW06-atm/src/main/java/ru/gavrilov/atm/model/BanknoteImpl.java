package ru.gavrilov.atm.model;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public class BanknoteImpl implements Banknote {

    private Nominal nominal;

    public BanknoteImpl(Nominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public Nominal nominal() {
        return nominal;
    }

    @Override
    public String toString() {
        return "CASH_" + nominal.value();
    }
}
