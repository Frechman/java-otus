package ru.gavrilov.atm.observer;

import ru.gavrilov.atm.observer.cmd.DepartmentCmd;

/**
 * @author gavrilov-sv
 * created on 05.06.2020
 */
public interface Subject {

    void register(Observer listener);

    void unregister(Observer listener);

    void notifySubscribers(DepartmentCmd departmentCmd);
}
