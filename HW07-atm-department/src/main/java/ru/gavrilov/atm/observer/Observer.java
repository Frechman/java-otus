package ru.gavrilov.atm.observer;

import ru.gavrilov.atm.observer.cmd.DepartmentCmd;

/**
 * @author gavrilov-sv
 * created on 04.06.2020
 */
public interface Observer {

    void update(DepartmentCmd departmentCmd);
}
