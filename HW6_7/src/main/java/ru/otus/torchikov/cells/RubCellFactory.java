package ru.otus.torchikov.cells;


import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Cell factory for Russian rubles
 */
public class RubCellFactory extends CellFactory {

	@Override
	public Set<Cell> createCells() {
		Set<Cell> cells = new HashSet<>();
		for (Nominal nominal : Currency.RUB.getNominals()) {
			cells.add(new Cell(Currency.RUB, nominal));
		}
		return cells;
	}

	@Override
	Currency getCurrency() {
		return Currency.RUB;
	}
}
