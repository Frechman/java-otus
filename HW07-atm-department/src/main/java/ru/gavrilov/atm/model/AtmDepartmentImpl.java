package ru.gavrilov.atm.model;

import ru.gavrilov.atm.api.Atm;
import ru.gavrilov.atm.api.AtmDepartment;
import ru.gavrilov.atm.observer.cmd.DepartmentBalanceCmd;
import ru.gavrilov.atm.command.ManageAtmStateCmd;
import ru.gavrilov.atm.observer.DepartmentManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public class AtmDepartmentImpl implements AtmDepartment {

    private final Map<String, ManageAtmStateCmd> managersAtmState = new HashMap<>();
    private final DepartmentManager balanceManager = new DepartmentManager();

    public AtmDepartmentImpl() {
    }

    public AtmDepartmentImpl(List<Atm> atms) {
        for (Atm atm : atms) {
            saveAtmState(atm);

            balanceManager.register(atm);
        }
    }

    private void saveAtmState(Atm atm) {
        var cmd = new ManageAtmStateCmd(atm);
        cmd.execute();
        managersAtmState.put(atm.getCode(), cmd);
    }

    @Override
    public void restoreAllAtm() {
        managersAtmState.values().forEach(ManageAtmStateCmd::undo);
        System.out.println("Restored states all ATMs");
    }

    @Override
    public void departmentBalance() {
        var departmentBalanceCmd = new DepartmentBalanceCmd();
        balanceManager.notifySubscribers(departmentBalanceCmd);
    }

    @Override
    public void addAtm(Atm atm) {
        saveAtmState(atm);
        balanceManager.register(atm);
    }

    @Override
    public void removeAtm(Atm atm) {
        managersAtmState.remove(atm.getCode());
        balanceManager.unregister(atm);
    }
}
