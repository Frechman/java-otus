package ru.gavrilov.atm.command;

import ru.gavrilov.atm.exception.NotEnoughMoneyException;
import ru.gavrilov.atm.model.AtmCell;
import ru.gavrilov.atm.model.Banknote;
import ru.gavrilov.atm.model.Nominal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class WithdrawCommand implements Transactional {

    private long amount;
    private List<AtmCell> atmCells;

    public WithdrawCommand(long amount, List<AtmCell> atmCells) {
        this.amount = amount;
        this.atmCells = atmCells;
    }

    @Override
    public void execute() {
        atmCells.sort(Comparator.comparing(AtmCell::nominal, Comparator.comparingLong(Nominal::value)).reversed());

        long sum = amount;

        List<Banknote> moneyForWithdraw = new ArrayList<>();
        for (AtmCell cell : atmCells) {
            Nominal nominal = cell.nominal();

            while (sum - nominal.value() >= 0 && cell.count() > 0) {
                Banknote banknote = cell.take();
                sum = sum - nominal.value();

                moneyForWithdraw.add(banknote);
            }
        }

        if (sum != 0) {
            returnMoneyToAtmCells(moneyForWithdraw);
            throw new NotEnoughMoneyException("Not enough money!");
        } else {
            System.out.println("Withdrawn amount: " + amount + " " + moneyForWithdraw);
        }
    }

    private void returnMoneyToAtmCells(List<Banknote> resultMoney) {
        resultMoney.stream()
                .collect(Collectors.groupingBy(Banknote::nominal, Collectors.toList()))
                .forEach((nominal, banknotes) -> {
                    for (AtmCell cell : atmCells) {
                        if (cell.nominal() == nominal) {
                            cell.put(banknotes);
                        }
                    }
                });
    }
}
