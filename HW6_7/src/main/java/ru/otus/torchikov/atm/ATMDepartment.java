package ru.otus.torchikov.atm;

import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergei on 23.05.17.
 *
 */
public class ATMDepartment implements ATM{
    private List<ATM> atms = new ArrayList<>();

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public void removeATM(ATM atm) {
        atms.remove(atm);
    }

    @Override
    public void printBalance() {
        atms.forEach(ATM::printBalance);
    }

    @Override
    public void saveDefaultState() {
        atms.forEach(ATM::saveDefaultState);
    }

    @Override
    public void restoreDefaultState() {
        atms.forEach(ATM::restoreDefaultState);
    }

    @Override
    public void addMoney(Currency currency, Nominal nominal, long count) {
        atms.forEach(a -> a.addMoney(currency, nominal, count));
    }

    @Override
    public void withdraw(Currency currency, long amount) {
        atms.forEach(a -> a.withdraw(currency, amount));
    }
}
