package ru.gavrilov.atm.command;

import ru.gavrilov.atm.Nominal;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class DepositCommand implements Transactional {

    private List<Long> amount;
    private Map<Nominal, Long> cassette;

    public DepositCommand(List<Long> amount, Map<Nominal, Long> cassette) {
        this.amount = amount;
        this.cassette = cassette;
    }

    @Override
    public void execute() {
        for (Long cash : amount) {
            Optional<Nominal> nominalByValue = Nominal.getByValue(cash);

            nominalByValue.ifPresent(nominal -> {
                Long count = cassette.getOrDefault(nominal, 0L);
                cassette.put(nominal, count + 1);
            });
        }
    }
}
