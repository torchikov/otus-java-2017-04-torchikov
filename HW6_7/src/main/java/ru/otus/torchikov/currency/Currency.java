package ru.otus.torchikov.currency;


import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 *
 */
public enum Currency {
	RUB("Rubles", Nominal.ONE_HUNDRED, Nominal.FIVE_HUNDRED, Nominal.ONE_THOUSAND, Nominal.FIVE_THOUSAND),
	USD("US Dollars", Nominal.ONE, Nominal.TWO, Nominal.FIVE, Nominal.TEN, Nominal.TWENTY, Nominal.FIFTY, Nominal.ONE_HUNDRED),
	EUR("Euros", Nominal.FIVE, Nominal.TEN, Nominal.TWENTY, Nominal.FIFTY, Nominal.ONE_HUNDRED, Nominal.FIVE_HUNDRED)
	;

	private Nominal[] nominals;
	private String name;

	Currency(String name, Nominal...nominals) {
		this.nominals = nominals;
		this.name = name;
	}

	public Nominal[] getNominals() {
		return nominals;
	}

	public String getName() {
		return name;
	}
}
