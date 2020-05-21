package ru.gavrilov.atm.command;

import ru.gavrilov.atm.model.Bankcell;
import ru.gavrilov.atm.model.Banknote;
import ru.gavrilov.atm.model.Nominal;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gavrilov-sv
 * created on 16.05.2020
 */
public class DepositCommand implements Transactional {

    private List<Banknote> money;
    private Map<Nominal, Bankcell> cassettes;

    public DepositCommand(List<Banknote> money, List<Bankcell> cassettes) {
        this.money = money;
        this.cassettes = cassettes.stream().collect(Collectors.toMap(Bankcell::nominal, Function.identity()));
    }

    @Override
    public void execute() {
        Set<Nominal> notAvailableForDeposit = getNominalNotAvailableForDeposit(money, cassettes);

        if (!notAvailableForDeposit.isEmpty()) {
            System.out.println("nominal which not available for deposit: " + notAvailableForDeposit.toString());
            //maybe an exception
        }

        for (Banknote banknote : money) {
            Nominal nominal = banknote.nominal();

            Bankcell bankcell = cassettes.get(nominal);
            if (bankcell != null) {
                bankcell.put(banknote);
            }
        }
        System.out.println("Introduced cash: " + money);
    }

    private Set<Nominal> getNominalNotAvailableForDeposit(final List<Banknote> banknotesForDeposit,
                                                          final Map<Nominal, Bankcell> cassettes) {
        return banknotesForDeposit.stream()
                .map(Banknote::nominal)
                .filter(n -> !cassettes.containsKey(n))
                .collect(Collectors.toSet());
    }
}
