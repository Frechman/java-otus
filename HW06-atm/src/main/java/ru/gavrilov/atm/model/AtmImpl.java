package ru.gavrilov.atm.model;

import ru.gavrilov.atm.command.BalanceCommand;
import ru.gavrilov.atm.command.DepositCommand;
import ru.gavrilov.atm.command.Transactional;
import ru.gavrilov.atm.command.WithdrawCommand;
import ru.gavrilov.atm.exception.NotEnoughMoneyException;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmImpl implements Atm {

    private List<AtmCell> cassettes;

    public AtmImpl(List<AtmCell> cassettes) {
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
}
