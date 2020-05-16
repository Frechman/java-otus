package ru.gavrilov.atm;

import ru.gavrilov.atm.command.BalanceCommand;
import ru.gavrilov.atm.command.DepositCommand;
import ru.gavrilov.atm.command.Transactional;
import ru.gavrilov.atm.command.WithdrawCommand;

import java.util.List;
import java.util.Map;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class AtmImpl implements Atm {

    private Map<Nominal, Long> cassettes;

    public AtmImpl(Map<Nominal, Long> cassettes) {
        this.cassettes = cassettes;
    }

    @Override
    public void withdraw(long amount) {
        Transactional withdrawCommand = new WithdrawCommand(amount, cassettes);
        withdrawCommand.execute();
    }

    @Override
    public void deposit(List<Long> amount) {
        Transactional depositCommand = new DepositCommand(amount, cassettes);
        depositCommand.execute();
    }

    @Override
    public void balance() {
        Transactional balanceCommand = new BalanceCommand(cassettes);
        balanceCommand.execute();
    }
}
