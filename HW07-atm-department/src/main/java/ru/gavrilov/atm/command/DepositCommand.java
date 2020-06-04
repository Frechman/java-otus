package ru.gavrilov.atm.command;

import ru.gavrilov.atm.model.AtmCell;
import ru.gavrilov.atm.model.Banknote;
import ru.gavrilov.atm.model.Nominal;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class DepositCommand implements Transactional {

    private Map<Nominal, List<Banknote>> money;
    private Map<Nominal, AtmCell> nominalAtmCellMap;

    public DepositCommand(List<Banknote> money, List<AtmCell> atmCells) {
        this.money = money.stream().collect(Collectors.groupingBy(Banknote::nominal, toList()));
        this.nominalAtmCellMap = atmCells.stream().collect(Collectors.toMap(AtmCell::nominal, Function.identity()));
    }

    @Override
    public void execute() {
        Set<Nominal> notAvailableForDeposit = getNominalNotAvailableForDeposit(money, nominalAtmCellMap);

        if (!notAvailableForDeposit.isEmpty()) {
            System.out.println("Nominal which not available for deposit: " + notAvailableForDeposit.toString());
            //maybe an exception
        }

        for (Map.Entry<Nominal, List<Banknote>> entry : money.entrySet()) {
            Nominal nominal = entry.getKey();
            List<Banknote> banknotes = money.get(nominal);

            AtmCell atmCell = nominalAtmCellMap.get(nominal);
            if (atmCell != null) {
                atmCell.put(banknotes);
            }
        }
        System.out.println("Introduced cash: " + money.values());
    }

    private Set<Nominal> getNominalNotAvailableForDeposit(final Map<Nominal, List<Banknote>> banknotesForDeposit,
                                                          final Map<Nominal, AtmCell> cassettes) {
        return banknotesForDeposit.keySet().stream()
                .filter(n -> !cassettes.containsKey(n))
                .collect(Collectors.toSet());
    }
}
