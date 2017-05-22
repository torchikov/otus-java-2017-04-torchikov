package ru.otus.torchikov.managers;


import ru.otus.torchikov.cells.Cell;
import ru.otus.torchikov.cells.CellFactory;
import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Use for cell managing
 */
public final class CellManager {
    private static final CellManager INSTANCE = new CellManager();
    private Map<Currency, Set<Cell>> cells = new HashMap<>();

    private CellManager() {
    }

    public static CellManager getInstance() {
        return INSTANCE;
    }

    public void createCells(Currency currency) {
        Set<Cell> currentCells = CellFactory.getInstance(currency).getCells();
        cells.put(currency, currentCells);
    }

    public void addMoney(Currency currency, Nominal nominal, long count) {
        checkIsCurrencyExist(currency);
        checkIsNominalExist(currency, nominal);
        cells.get(currency).stream()
                .filter(c -> c.getNominal() == nominal)
                .forEach(c -> c.setCount(c.getCount() + count));
    }


    void doWithdrawOperation(Currency currency, Map<Cell, Long> withdrawMap) {
        withdrawMap.forEach((cell, count) -> cells.get(currency).stream()
                .filter(c -> c.equals(cell))
                .forEach(c -> c.setCount(c.getCount() - count)));
    }

    public long getBalance(Currency currency) {
        checkIsCurrencyExist(currency);
        return cells.get(currency).stream()
                .mapToLong(Cell::getAmount)
                .sum();
    }

    Set<Currency> getAvailableCurrencies() {
        return cells.keySet();
    }

    private void checkIsCurrencyExist(Currency currency) {
        boolean isExist = cells.containsKey(currency);
        if (!isExist) {
            throw new IllegalArgumentException("The ATM doesn't support " + currency.getName());
        }
    }

    private void checkIsNominalExist(Currency currency, Nominal nominal) {
        boolean isExist = cells.entrySet().stream()
                .filter(e -> e.getKey() == currency)
                .flatMap(e -> e.getValue().stream())
                .anyMatch(c -> c.getNominal() == nominal);
        if (!isExist) {
            throw new IllegalArgumentException("The currency " + currency.getName() +
                    " doesn't support notes by nominal " + nominal.getNominalValue());
        }
    }

    Map<Nominal, Long> getAvailableNotes(Currency currency) {
        return cells.get(currency).stream()
                .filter(c -> c.getCount() != 0)
                .collect(Collectors.toMap(Cell::getNominal, Cell::getCount));
    }


}
