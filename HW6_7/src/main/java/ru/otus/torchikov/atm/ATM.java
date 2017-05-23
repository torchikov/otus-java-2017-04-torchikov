package ru.otus.torchikov.atm;

import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by sergei on 23.05.17.
 * Interface with general ATM methods
 */
public interface ATM {
    void printBalance();

    void saveDefaultState();

    void restoreDefaultState();

    void addMoney(Currency currency, Nominal nominal, long count);

    void withdraw(Currency currency, long amount);

}
