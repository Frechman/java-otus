package ru.gavrilov.atm.observer.cmd;

import ru.gavrilov.atm.api.Atm;

/**
 * @author gavrilov-sv
 * created on 05.06.2020
 */
public interface DepartmentCmd {

    void execute(Atm atm);
}
