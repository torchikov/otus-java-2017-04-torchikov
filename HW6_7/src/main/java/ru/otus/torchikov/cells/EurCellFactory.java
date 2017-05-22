package ru.otus.torchikov.cells;


import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Cell factory for euros
 */
public class EurCellFactory extends CellFactory {
	@Override
	public Set<Cell> createCells() {
		Set<Cell> cells = new HashSet<>();
		for (Nominal nominal : Currency.EUR.getNominals()) {
			cells.add(new Cell(Currency.EUR, nominal));
		}
		return cells;
	}

	@Override
	Currency getCurrency() {
		return Currency.EUR;
	}
}
