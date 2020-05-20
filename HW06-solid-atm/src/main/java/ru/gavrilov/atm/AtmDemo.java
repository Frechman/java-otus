package ru.gavrilov.atm;

import ru.gavrilov.atm.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.gavrilov.atm.model.BanknoteEnum.*;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmDemo {

    public static void main(String[] args) {
        Bankcell bankCell = new BankCellImpl(Nominal.TEN, Arrays.asList(BANKNOTE_10, BANKNOTE_10, BANKNOTE_10));
        Bankcell bankCell2 = new BankCellImpl(Nominal.HUNDRED, Arrays.asList(BANKNOTE_100, BANKNOTE_100, BANKNOTE_100));
        Bankcell bankCell3 = new BankCellImpl(Nominal.THOUSAND, Arrays.asList(BANKNOTE_1000, BANKNOTE_1000, BANKNOTE_1000));

        List<Bankcell> initCassette = new ArrayList<>(Arrays.asList(bankCell, bankCell2, bankCell3));

        Atm atm = new AtmImpl(initCassette);
        atm.balance();
        System.out.println();

        atm.deposit(Arrays.asList(BANKNOTE_100, BANKNOTE_100, BANKNOTE_10, BANKNOTE_10, BANKNOTE_1000));
        atm.balance();
        System.out.println();

        atm.deposit(Arrays.asList(BANKNOTE_100, BANKNOTE_50));
        atm.balance();
        System.out.println();

        atm.withdraw(1650);
        atm.balance();
    }
}
