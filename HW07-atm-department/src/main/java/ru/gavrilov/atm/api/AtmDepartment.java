package ru.gavrilov.atm.api;

/**
 * @author gavrilov-sv
 * created on 27.05.2020
 */
public interface AtmDepartment {

    /**
     * Restored states ATMs
     */
    void restoreAllAtm();

    /**
     * Getting balance at all ATMs
     */
    void departmentBalance();

    /**
     * Add ATM to Department
     */
    void addAtm(Atm atm);

    /**
     * Remove ATM from Department
     */
    void removeAtm(Atm atm);
}
