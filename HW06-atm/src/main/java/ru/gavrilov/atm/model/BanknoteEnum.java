package ru.gavrilov.atm.model;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public enum BanknoteEnum implements Banknote{

    BANKNOTE_10(Nominal.TEN),
    BANKNOTE_50(Nominal.FIFTY),
    BANKNOTE_100(Nominal.HUNDRED),
    BANKNOTE_1000(Nominal.THOUSAND);

    private Nominal nominal;

    BanknoteEnum(Nominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public Nominal nominal() {
        return nominal;
    }
}
