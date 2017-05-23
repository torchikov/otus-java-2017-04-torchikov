package ru.otus.torchikov.state;

import ru.otus.torchikov.cells.Cell;
import ru.otus.torchikov.currency.Currency;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sergei on 23.05.17.
 */
public class DefaultCellsState implements State<Map<Currency, Set<Cell>>> {

    private Map<Currency, Set<Cell>> state = new HashMap<>();


    @Override
    public Map<Currency, Set<Cell>> getState() {
        return state;
    }

    @Override
    public void setState(Map<Currency, Set<Cell>> state) {

        this.state = state;
    }
}
