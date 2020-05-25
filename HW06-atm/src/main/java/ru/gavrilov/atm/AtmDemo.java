package ru.gavrilov.atm;

import ru.gavrilov.atm.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.gavrilov.atm.model.Nominal.*;


/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmDemo {

    public static void main(String[] args) {
        AtmCell bankCell = new AtmCellImpl(TEN,
                Arrays.asList(new BanknoteImpl(TEN), new BanknoteImpl(TEN), new BanknoteImpl(TEN)));

        AtmCell bankCell2 = new AtmCellImpl(HUNDRED,
                Arrays.asList(new BanknoteImpl(HUNDRED), new BanknoteImpl(HUNDRED), new BanknoteImpl(TEN)));

        AtmCell bankCell3 = new AtmCellImpl(THOUSAND,
                Arrays.asList(new BanknoteImpl(THOUSAND), new BanknoteImpl(THOUSAND), new BanknoteImpl(THOUSAND)));

        List<AtmCell> initCassette = new ArrayList<>(Arrays.asList(bankCell, bankCell2, bankCell3));

        Atm atm = new AtmImpl(initCassette);
        atm.balance();
        System.out.println();

        atm.deposit(Arrays.asList(new BanknoteImpl(HUNDRED), new BanknoteImpl(HUNDRED),
                new BanknoteImpl(TEN), new BanknoteImpl(TEN), new BanknoteImpl(THOUSAND)));
        atm.balance();
        System.out.println();

        atm.deposit(Arrays.asList(new BanknoteImpl(HUNDRED), new BanknoteImpl(FIFTY)));
        atm.balance();
        System.out.println();

        atm.withdraw(1650);
        atm.balance();
    }
}
