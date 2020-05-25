package ru.gavrilov.atm.command;

import ru.gavrilov.atm.model.AtmCell;

import java.util.List;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class BalanceCommand implements Transactional {

    private List<AtmCell> cassette;

    public BalanceCommand(List<AtmCell> cassette) {
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
