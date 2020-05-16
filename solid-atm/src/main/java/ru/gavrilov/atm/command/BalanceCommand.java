package ru.gavrilov.atm.command;

import ru.gavrilov.atm.Nominal;

import java.util.Map;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class BalanceCommand implements Transactional {

    private Map<Nominal, Long> cassette;

    public BalanceCommand(Map<Nominal, Long> cassette) {
        this.cassette = cassette;
    }

    @Override
    public void execute() {
        long sum = cassette.entrySet().stream()
                .mapToLong(cassette -> cassette.getKey().value() * cassette.getValue())
                .sum();

        System.out.print("balance: " + sum + " ");
        System.out.println(cassette.toString());
    }
}
