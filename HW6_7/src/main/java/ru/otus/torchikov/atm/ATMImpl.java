package ru.otus.torchikov.atm;


import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.exceptions.CurrencyUnavailableException;
import ru.otus.torchikov.exceptions.UnsupportedWithdrawOperationException;
import ru.otus.torchikov.managers.CellManager;
import ru.otus.torchikov.managers.WithdrawManager;
import ru.otus.torchikov.nominals.Nominal;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Class which represents ATMImpl
 */
public class ATMImpl implements ATM{
    private CellManager cellManager;
    private WithdrawManager withdrawManager;
    private String name;

    public ATMImpl(String name, Currency... currencies) {
        this.name = name;
        cellManager = new CellManager();
        withdrawManager = new WithdrawManager(cellManager);
        cellManager.createCells(withdrawManager, currencies);
    }

    public String getName() {
        return name;
    }

    @Override
    public void addMoney(Currency currency, Nominal nominal, long count) {
        try {
            cellManager.addMoney(currency, nominal, count);
        }catch (CurrencyUnavailableException e) {
            System.out.println("Te ATM " + name + " doesn't support " + currency.getName() + "\n");
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: add money.");
        }
    }

    @Override
    public void withdraw(Currency currency, long amount) {
        try {
            System.out.println("Requested " + amount + " " + currency.getName() + " from ATM " + name);
            withdrawManager.withdraw(currency, amount);
            System.out.println();
        } catch (CurrencyUnavailableException e) {
            System.out.println("Te ATM " + name + " doesn't support " + currency.getName() + "\n");
        } catch (UnsupportedWithdrawOperationException e) {
            System.err.println(e.getMessage());
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: withdraw.");
        }
    }

    @Override
    public void printBalance() {
        try {
            System.out.println("Balance " + "in ATM " + name +" is: ");
            System.out.println(cellManager.getBalance());
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: print balance.");
        }
    }


    @Override
    public void saveDefaultState() {
        cellManager.saveDefaultState();
        System.out.println("ATM " + name + " has saved default state.");
    }

    @Override
    public void restoreDefaultState() {
        try {
            cellManager.restoreDefaultState();
            System.out.println("ATM " + name + " was been restored");
        } catch (Throwable e) {
            System.err.println(e.getMessage() + ". Operation: restore default state.");
        }
    }

}
