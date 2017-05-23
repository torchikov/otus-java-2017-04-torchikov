package ru.otus.torchikov.cells;


import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.managers.WithdrawManager;

import java.util.Set;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Abstract Factory for creating cells
 */
public abstract class CellFactory {
    private static final CellFactory RUB_FACTORY = new RubCellFactory();
    private static final CellFactory USD_FACTORY = new UsdCellFactory();
    private static final CellFactory EUR_FACTORY = new EurCellFactory();

    public static CellFactory getInstance(Currency currency) {
        CellFactory factory = null;
        if (currency == Currency.RUB) {
            factory = RUB_FACTORY;
        } else if (currency == Currency.USD) {
            factory = USD_FACTORY;
        } else if (currency == Currency.EUR) {
            factory = EUR_FACTORY;
        }
        return factory;
    }

    public Set<Cell> getCells(WithdrawManager withdrawManager) {
        Set<Cell> cells = createCells();
        withdrawManager.createWithdrawHandlers(getCurrency(), cells);
        return cells;
    }

    abstract Set<Cell> createCells();

    abstract Currency getCurrency();

    public Cell copyCell(Cell cell) {
        Cell copy = new Cell(cell.getCurrency(), cell.getNominal());
        copy.setCount(cell.getCount());
        return copy;
    }
}
