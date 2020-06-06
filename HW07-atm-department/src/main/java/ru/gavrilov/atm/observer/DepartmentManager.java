package ru.gavrilov.atm.observer;

import ru.gavrilov.atm.observer.cmd.DepartmentCmd;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavrilov-sv
 * created on 05.06.2020
 */
public class DepartmentManager implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public DepartmentManager() {
    }

    @Override
    public void register(Observer listener) {
        observers.add(listener);
    }

    @Override
    public void unregister(Observer listener) {
        observers.remove(listener);
    }

    @Override
    public void notifySubscribers(DepartmentCmd departmentCmd) {
        observers.forEach(observer -> observer.update(departmentCmd));
    }
}
