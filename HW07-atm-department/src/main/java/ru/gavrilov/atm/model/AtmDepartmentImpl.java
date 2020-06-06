package ru.gavrilov.atm.model;

import ru.gavrilov.atm.api.Atm;
import ru.gavrilov.atm.api.AtmDepartment;
import ru.gavrilov.atm.observer.cmd.DepartmentBalanceCmd;
import ru.gavrilov.atm.command.ManageAtmStateCmd;
import ru.gavrilov.atm.observer.DepartmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public class AtmDepartmentImpl implements AtmDepartment {

    private List<Atm> atms = new ArrayList<>();
    private List<ManageAtmStateCmd> manageAtmStateCmds = new ArrayList<>();
    private final DepartmentManager balanceManager = new DepartmentManager();

    public AtmDepartmentImpl() {
        this.atms = new ArrayList<>();
        this.manageAtmStateCmds = new ArrayList<>();
    }

    public AtmDepartmentImpl(List<Atm> atms) {
        for (Atm atm : atms) {
            this.atms.add(atm);
            this.saveAtmState(atm);
            balanceManager.register(atm);
        }
    }

    private void saveAtmState(Atm atm) {
        var cmd = new ManageAtmStateCmd(atm);
        cmd.execute();
        manageAtmStateCmds.add(cmd);
    }

    @Override
    public void restoreAllAtm() {
        manageAtmStateCmds.forEach(ManageAtmStateCmd::undo);
        System.out.println("Restored states all ATMs");
    }

    @Override
    public void departmentBalance() {
        var departmentBalanceCmd = new DepartmentBalanceCmd();
        balanceManager.notifySubscribers(departmentBalanceCmd);
    }

    @Override
    public void addAtm(Atm atm) {
        atms.add(atm);
        saveAtmState(atm);
        balanceManager.register(atm);
    }

    @Override
    public void removeAtm(Atm atm) {
        atms.remove(atm);
        balanceManager.unregister(atm);
    }
}
