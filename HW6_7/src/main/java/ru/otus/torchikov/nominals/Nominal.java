package ru.otus.torchikov.nominals;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Enumeration for note's nominal
 */
public enum Nominal {
	ONE(1),
	TWO(2),
	FIVE(5),
	TEN(10),
	TWENTY(20),
	FIFTY(50),
	ONE_HUNDRED(100),
	FIVE_HUNDRED(500),
	ONE_THOUSAND(1000),
	FIVE_THOUSAND(5000)
	;

	private int nominalValue;

	Nominal(int nominalValue) {
		this.nominalValue = nominalValue;
	}

	public int getNominalValue() {
		return nominalValue;
	}
}
