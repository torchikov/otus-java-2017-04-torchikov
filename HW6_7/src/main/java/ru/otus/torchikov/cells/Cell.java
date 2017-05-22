package ru.otus.torchikov.cells;


import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Class which represents ATM's cell
 */
public class Cell {
	private Currency currency;
	private Nominal nominal;
	private long count;

	Cell(Currency currency, Nominal nominal) {
		this.currency = currency;
		this.nominal = nominal;
	}

	public long getAmount() {
		return count * nominal.getNominalValue();
	}

	public Nominal getNominal() {
		return nominal;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getCount() {
		return count;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cell cell = (Cell) o;

		return getCurrency() == cell.getCurrency() && nominal == cell.nominal;
	}

	@Override
	public int hashCode() {
		int result = getCurrency().getName().hashCode();
		result = 31 * result + nominal.getNominalValue();
		return result;
	}

	@Override
	public String toString() {
		return "Cell{" +
				"currency=" + currency +
				", nominal=" + nominal +
				", count=" + count +
				'}';
	}
}
