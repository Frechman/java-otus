package ru.gavrilov.atm.command;

import ru.gavrilov.atm.model.Bankcell;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class BalanceCommand implements Transactional {

    private List<Bankcell> cassette;

    public BalanceCommand(List<Bankcell> cassette) {
        this.cassette = cassette;
    }

    @Override
    public void execute() {
        long sum = cassette.stream()
                .mapToLong(cassette -> cassette.nominal().value() * cassette.count())
                .sum();

        System.out.println("Balance: " + sum + " " + cassette);
    }
}
