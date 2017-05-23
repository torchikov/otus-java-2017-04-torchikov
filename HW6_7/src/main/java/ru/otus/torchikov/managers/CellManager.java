package ru.otus.torchikov.managers;


import ru.otus.torchikov.cells.Cell;
import ru.otus.torchikov.cells.CellFactory;
import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.exceptions.CurrencyUnavailableException;
import ru.otus.torchikov.nominals.Nominal;
import ru.otus.torchikov.state.DefaultCellsState;
import ru.otus.torchikov.state.State;
import ru.otus.torchikov.state.StateCareTacker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Use for cell managing
 */
public final class CellManager {
    private Map<Currency, Set<Cell>> cells = new HashMap<>();
    private StateCareTacker<Map<Currency, Set<Cell>>> careTacker = new StateCareTacker<>();

    public CellManager() {
    }


    public void createCells(WithdrawManager withdrawManager, Currency... currencies) {
        for (Currency currency : currencies) {
            Set<Cell> currentCells = CellFactory.getInstance(currency).getCells(withdrawManager);
            cells.put(currency, currentCells);
        }
    }

    private Cell copyCell(Cell cell) {
        return CellFactory.getInstance(cell.getCurrency()).copyCell(cell);
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

    public String getBalance() {
        StringBuilder result = new StringBuilder();
        for (Currency currency : getAvailableCurrencies()) {
            long balance = cells.get(currency).stream()
                    .mapToLong(Cell::getAmount)
                    .sum();
            result.append(balance).append(" ").append(currency.getName()).append("\n");
        }
        return result.toString();
    }

    Set<Currency> getAvailableCurrencies() {
        return cells.keySet();
    }

    Map<Nominal, Long> getAvailableNotes(Currency currency) {
        return cells.get(currency).stream()
                .filter(c -> c.getCount() != 0)
                .collect(Collectors.toMap(Cell::getNominal, Cell::getCount));
    }

    public void saveDefaultState() {
        State<Map<Currency, Set<Cell>>> defaultState = new DefaultCellsState();
        defaultState.setState(deepCopyState(this.cells));
        careTacker.addState(defaultState);
    }

    public void restoreDefaultState() {
        State<Map<Currency, Set<Cell>>> defaultState = careTacker.getState();
        this.cells = deepCopyState(defaultState.getState());
    }

    private Map<Currency, Set<Cell>> deepCopyState(Map<Currency, Set<Cell>> state) {
        Map<Currency, Set<Cell>> result = new HashMap<>();
        for (Map.Entry<Currency, Set<Cell>> entry : state.entrySet()) {
            Set<Cell> resultCells = new HashSet<>();
            for (Cell cell : entry.getValue()) {
                resultCells.add(copyCell(cell));
            }
            result.put(entry.getKey(), resultCells);
        }
        return result;
    }

    private void checkIsCurrencyExist(Currency currency) {
        boolean isExist = cells.containsKey(currency);
        if (!isExist) {
            throw new CurrencyUnavailableException();
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


}
