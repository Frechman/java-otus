package ru.gavrilov.atm.command;

import ru.gavrilov.atm.model.Bankcell;
import ru.gavrilov.atm.model.Banknote;
import ru.gavrilov.atm.model.Nominal;
import ru.gavrilov.atm.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class WithdrawCommand implements Transactional {

    private long amount;
    private List<Bankcell> cassettes;

    public WithdrawCommand(long amount, List<Bankcell> cassettes) {
        this.amount = amount;
        this.cassettes = cassettes;
    }

    @Override
    public void execute() {
        cassettes.sort(Comparator.comparing(Bankcell::nominal, Comparator.comparingLong(Nominal::value)).reversed());

        long sum = amount;

        List<Banknote> moneyForWithdraw = new ArrayList<>();
        for (Bankcell cassette : cassettes) {
            Nominal nominal = cassette.nominal();

            while (sum - nominal.value() >= 0 && cassette.count() > 0) {
                Banknote take = cassette.take();
                sum = sum - nominal.value();

                moneyForWithdraw.add(take);
            }
        }

        if (sum != 0) {
            returnMoneyToCassette(moneyForWithdraw);
            throw new NotEnoughMoneyException("Not enough money!");
        } else {
            System.out.println("Withdrawn amount: " + amount + " " + moneyForWithdraw);
        }
    }

    private void returnMoneyToCassette(List<Banknote> resultMoney) {
        for (Banknote banknote : resultMoney) {
            Nominal nominal = banknote.nominal();
            for (Bankcell cassette : cassettes) {
                if (cassette.nominal() == nominal){
                    cassette.put(banknote);
                }
            }
        }
    }
}
