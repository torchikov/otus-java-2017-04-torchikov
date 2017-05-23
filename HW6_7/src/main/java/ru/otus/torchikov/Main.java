package ru.otus.torchikov;


import ru.otus.torchikov.atm.ATM;
import ru.otus.torchikov.atm.ATMDepartment;
import ru.otus.torchikov.atm.ATMImpl;
import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 *
 */
public class Main {
	public static void main(String[] args) {
        ATM atmInRightCorner = new ATMImpl("Right corner", Currency.RUB, Currency.USD);
		atmInRightCorner.addMoney(Currency.RUB, Nominal.ONE_HUNDRED, 6);
		atmInRightCorner.addMoney(Currency.RUB, Nominal.ONE_THOUSAND, 12);
		atmInRightCorner.addMoney(Currency.USD, Nominal.FIVE, 9);
		atmInRightCorner.addMoney(Currency.USD, Nominal.ONE_HUNDRED, 2);

        ATM atmInLeftCorner = new ATMImpl("Left corner", Currency.RUB);
        atmInLeftCorner.addMoney(Currency.RUB, Nominal.ONE_HUNDRED, 13);
        atmInLeftCorner.addMoney(Currency.RUB, Nominal.FIVE_HUNDRED, 7);
        atmInLeftCorner.addMoney(Currency.RUB, Nominal.FIVE_THOUSAND, 4);

        ATM atmInCentre = new ATMImpl("Centre", Currency.RUB, Currency.EUR);
        atmInCentre.addMoney(Currency.RUB, Nominal.ONE_HUNDRED, 9);
        atmInCentre.addMoney(Currency.RUB, Nominal.ONE_THOUSAND, 12);
        atmInCentre.addMoney(Currency.RUB, Nominal.FIVE_THOUSAND, 2);
        atmInCentre.addMoney(Currency.EUR, Nominal.FIVE, 13);
        atmInCentre.addMoney(Currency.EUR, Nominal.FIFTY, 7);
        atmInCentre.addMoney(Currency.EUR, Nominal.TWENTY, 4);

        ATMDepartment department = new ATMDepartment();
        department.addATM(atmInCentre);
        department.addATM(atmInRightCorner);
        department.addATM(atmInLeftCorner);

        department.printBalance();
        department.saveDefaultState();
        department.withdraw(Currency.EUR, 10);
        department.withdraw(Currency.RUB, 400);
        department.printBalance();
        department.restoreDefaultState();
        System.out.println();
        department.printBalance();
    }
}
