package ru.otus.torchikov;


import ru.otus.torchikov.atm.ATM;
import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 */
public class Main {
	public static void main(String[] args) {
        ATM atm = new ATM(Currency.RUB, Currency.USD);
        atm.addMoney(Currency.USD, Nominal.TEN, 4);
        atm.addMoney(Currency.USD, Nominal.ONE_HUNDRED, 4);

        atm.addMoney(Currency.RUB, Nominal.FIVE_THOUSAND, 1);
		atm.addMoney(Currency.RUB, Nominal.ONE_HUNDRED, 3);
		atm.addMoney(Currency.RUB, Nominal.FIVE_HUNDRED, 7);
		atm.switchOn();
		atm.printBalance(Currency.RUB);
		atm.printBalance(Currency.USD);
		atm.withdraw(Currency.RUB, 700);
		atm.withdraw(Currency.USD, 50);
		atm.printBalance(Currency.RUB);
		atm.printBalance(Currency.USD);
	}
}
