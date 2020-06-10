package ru.gavrilov.atm;

import ru.gavrilov.atm.api.Atm;
import ru.gavrilov.atm.api.AtmCell;
import ru.gavrilov.atm.memento.AtmMemento;
import ru.gavrilov.atm.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.gavrilov.atm.api.Nominal.*;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmDemo {

    public static void main(String[] args) {
        AtmCell bankCell = new AtmCellImpl(TEN, 3);
        AtmCell bankCell2 = new AtmCellImpl(HUNDRED, 3);
        AtmCell bankCell3 = new AtmCellImpl(THOUSAND, 3);

        List<AtmCell> initCassette = new ArrayList<>(Arrays.asList(bankCell, bankCell2, bankCell3));

        Atm atm = new AtmImpl("#3377733", initCassette);

        final AtmDepartmentImpl department = new AtmDepartmentImpl();
        department.addAtm(atm);

        atm.deposit(Arrays.asList(new BanknoteImpl(HUNDRED), new BanknoteImpl(HUNDRED),
                new BanknoteImpl(TEN), new BanknoteImpl(TEN), new BanknoteImpl(THOUSAND)));
        atm.balance();
        System.out.println();
        AtmMemento initialState = atm.saveState();
        atm.balance();
        System.out.println("Saving state...\n");

        atm.deposit(Arrays.asList(new BanknoteImpl(HUNDRED), new BanknoteImpl(FIFTY)));
        atm.balance();
        System.out.println();

        atm.withdraw(1650);
        atm.balance();

        System.out.println();

        department.departmentBalance();
        department.restoreAllAtm();
        atm.balance();

        System.out.println("\nRestore state...");
        atm.restoreState(initialState);
        department.departmentBalance();
    }
}
