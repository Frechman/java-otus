package ru.gavrilov.atm.model;

import ru.gavrilov.atm.api.Atm;
import ru.gavrilov.atm.api.AtmCell;
import ru.gavrilov.atm.api.Banknote;
import ru.gavrilov.atm.command.BalanceCommand;
import ru.gavrilov.atm.command.DepositCommand;
import ru.gavrilov.atm.command.Transactional;
import ru.gavrilov.atm.command.WithdrawCommand;
import ru.gavrilov.atm.exception.NotEnoughMoneyException;
import ru.gavrilov.atm.memento.AtmMemento;
import ru.gavrilov.atm.memento.AtmState;
import ru.gavrilov.atm.observer.cmd.DepartmentCmd;

import java.util.List;
import java.util.Objects;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmImpl implements Atm {

    private String code;
    private List<AtmCell> cassettes;

    public AtmImpl(String code, List<AtmCell> cassettes) {
        this.code = code;
        this.cassettes = cassettes;
    }

    @Override
    public void withdraw(long amount) {
        Transactional withdrawCommand = new WithdrawCommand(amount, cassettes);
        try {
            withdrawCommand.execute();
        } catch (NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deposit(List<Banknote> amount) {
        Transactional depositCommand = new DepositCommand(amount, cassettes);
        depositCommand.execute();
    }

    @Override
    public void balance() {
        Transactional balanceCommand = new BalanceCommand(cassettes);
        balanceCommand.execute();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void restoreState(AtmMemento memento) {
        cassettes = memento.getState().getCells();
    }

    @Override
    public AtmMemento saveState() {
        return new AtmMemento(this, new AtmState(cassettes));
    }

    @Override
    public void update(DepartmentCmd departmentCmd) {
        departmentCmd.execute(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtmImpl atm = (AtmImpl) o;
        return Objects.equals(code, atm.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
