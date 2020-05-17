package ru.gavrilov.atm.command;

import ru.gavrilov.atm.Nominal;

import java.util.*;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class WithdrawCommand implements Transactional {

    private long amount;
    private Map<Nominal, Long> cassettes;

    public WithdrawCommand(long amount, Map<Nominal, Long> cassettes) {
        this.amount = amount;
        this.cassettes = cassettes;
    }

    @Override
    public void execute() {
        var backupCassettes = new HashMap<>(cassettes); //need to do deep copy if map contains mutable objects

        long sum = amount;
        var resultMoney = new ArrayList<>();

        List<Map.Entry<Nominal, Long>> cassettes = new ArrayList<>(this.cassettes.entrySet());
        cassettes.sort(Comparator.comparingLong((Map.Entry<Nominal, Long> e) -> e.getKey().value()).reversed());

        for (var cassette : cassettes) {
            Nominal nominal = cassette.getKey();

            while (sum - nominal.value() >= 0 && cassette.getValue() > 0) {
                cassette.setValue(cassette.getValue() - 1);
                sum = sum - nominal.value();

                this.cassettes.put(nominal, this.cassettes.get(nominal) - 1); //original cassettes

                resultMoney.add(nominal.value());
            }
        }

        if (sum != 0) {
            this.cassettes.putAll(backupCassettes);

            System.out.println("Not enough money!");
        } else {
            System.out.println("Withdrawn amount: " + amount + " " + resultMoney);
        }
    }
}
