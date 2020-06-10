package ru.gavrilov.atm.observer.cmd;

import ru.gavrilov.atm.api.Atm;

/**
 * @author gavrilov-sv
 * created on 06.06.2020
 */
public class DepartmentBalanceCmd implements DepartmentCmd {

    @Override
    public void execute(Atm atm) {
        System.out.println("### Department balance ### ");
        System.out.print("ATM " + atm.getCode() + " ");
        atm.balance();
    }
}
