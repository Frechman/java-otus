package ru.gavrilov.atm;

import ru.gavrilov.atm.model.Atm;

/**
 * @author gavrilov-sv
 * created on 27.05.2020
 */
public interface AtmDepartment {

    void restoreAllAtm();

    void commonBalance();

    void addAtm(Atm atm);

    void removeAtm(Atm atm);
}
