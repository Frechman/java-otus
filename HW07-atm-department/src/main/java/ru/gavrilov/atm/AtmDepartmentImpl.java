package ru.gavrilov.atm;

import ru.gavrilov.atm.command.ManageAtmStateCmd;
import ru.gavrilov.atm.model.Atm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public class AtmDepartmentImpl implements AtmDepartment {

    private List<Atm> atms = new ArrayList<>();
    private List<ManageAtmStateCmd> manageAtmStateCmds = new ArrayList<>();

    public AtmDepartmentImpl() {
        this.atms = new ArrayList<>();
        this.manageAtmStateCmds = new ArrayList<>();
    }

    public AtmDepartmentImpl(List<Atm> atms) {
        for (Atm atm : atms) {
            this.atms.add(atm);
            this.saveAtmState(atm);
        }
    }

    private void saveAtmState(Atm atm) {
        final ManageAtmStateCmd cmd = new ManageAtmStateCmd(atm);
        cmd.execute();
        manageAtmStateCmds.add(cmd);
    }

    @Override
    public void restoreAllAtm() {
        manageAtmStateCmds.forEach(ManageAtmStateCmd::undo);
        System.out.println("Restored states all ATMs");
    }

    @Override
    public void commonBalance() {
        System.out.println("General balance: ");
    }

    @Override
    public void addAtm(Atm atm) {

    }

    @Override
    public void removeAtm(Atm atm) {

    }
}
