package ru.otus.torchikov.atm;


import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.exceptions.UnsupportedWithdrawOperationException;
import ru.otus.torchikov.managers.CellManager;
import ru.otus.torchikov.managers.WithdrawManager;
import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Class which represents ATM
 */
public class ATM {
    private CellManager cellManager = CellManager.getInstance();
    private WithdrawManager withdrawManager = WithdrawManager.getInstance();
    private boolean isSwitchedOn;

    public ATM(Currency... currencies) {
        for (Currency currency : currencies) {
            cellManager.createCells(currency);
        }
    }

    public void addMoney(Currency currency, Nominal nominal, long count) {
        try {
            checkIsSwitchedOff();
            cellManager.addMoney(currency, nominal, count);
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: add money.");
        }
    }


    public void withdraw(Currency currency, long amount) {
        try {
            checkIsSwitchedOn();
            System.out.println("Requested " + amount + " " + currency.getName());
            withdrawManager.withdraw(currency, amount);
        } catch (UnsupportedWithdrawOperationException e) {
            System.err.println(e.getMessage());
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: withdraw.");
        }
    }


    public void printBalance(Currency currency) {
        try {
            checkIsSwitchedOn();
            System.out.println("Balance is " + cellManager.getBalance(currency) + " " + currency.getName());
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: print balance.");
        }
    }

    public void switchOn() {
        if (!isSwitchedOn) {
            isSwitchedOn = true;
        }
    }

    public void swithOff() {
        if (isSwitchedOn) {
            isSwitchedOn = false;
        }
    }

    private void checkIsSwitchedOff() {
        if (isSwitchedOn) {
            throw new IllegalStateException("The ATM is working. " +
                    "Please switch ATM off and repeat");
        }
    }

    private void checkIsSwitchedOn() {
        if (!isSwitchedOn) {
            throw new IllegalStateException("The ATM isn't working. " +
                    "Please switch ATM on and repeat");
        }
    }
}
