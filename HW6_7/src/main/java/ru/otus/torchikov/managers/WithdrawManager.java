package ru.otus.torchikov.managers;


import ru.otus.torchikov.cells.Cell;
import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.exceptions.UnsupportedWithdrawOperationException;
import ru.otus.torchikov.nominals.Nominal;
import ru.otus.torchikov.withdrawal.WithdrawHandler;

import java.util.*;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Manage withdrawal operations
 */
public final class WithdrawManager {
	private static final WithdrawManager MANAGER = new WithdrawManager();
	private CellManager cellManager = CellManager.getInstance();
	private Map<Cell, Long> withdrawMap = new HashMap<>();
    private Map<Currency, List<WithdrawHandler>> handlers = new HashMap<>();

	private WithdrawManager() {
	}

	public static WithdrawManager getInstance() {
		return MANAGER;
	}

    public void createWithdrawHandlers(Currency currency, Set<Cell> cells) {
        List<WithdrawHandler> list = new ArrayList<>();
        cells.forEach(c -> list.add(new WithdrawHandler(c)));
        sortHandlers(list);
        handlers.put(currency, list);
    }

	private void sortHandlers(List<WithdrawHandler> handlers) {
		Comparator<WithdrawHandler> comparator = Comparator.comparingInt(h -> h.getCell().getNominal().getNominalValue());
		handlers.sort(comparator.reversed());
		for (int i = 0; i < handlers.size() - 1; i++) {
			WithdrawHandler current = handlers.get(i);
			WithdrawHandler next = handlers.get(i + 1);
			current.addNext(next);
		}
	}

	public void addToWithdrawMap(Cell cell, long count) {
		withdrawMap.put(cell, count);
	}

	public void withdraw(Currency currency, long required) {
		try {
            checkIsCurrencyExist(currency);
            List<WithdrawHandler> lisHandlers = handlers.get(currency);
            getFirstHandler(lisHandlers).executeWithdraw(currency, required);
			cellManager.doWithdrawOperation(currency, withdrawMap);
			System.out.println("Withdraw was successfully completed with notes:");
			withdrawMap.forEach((cell, count) -> System.out.println("Note nominal: " + cell.getNominal().getNominalValue()
					+ ", count: " + count + ", currency: " + currency.getName()));
		} catch (UnsupportedWithdrawOperationException e) {
            StringBuilder errorMessage = new StringBuilder("Withdraw operation isn't possible for ");
            errorMessage.append(required).append(" ").append(currency.getName()).append("\n");
            Map<Nominal, Long> availableNominals = cellManager.getAvailableNotes(currency);
            errorMessage.append("Available notes:").append("\n");
            availableNominals.forEach((n, c) -> errorMessage.append(n.getNominalValue())
                    .append(" ").append(currency.getName()).append(", count: ").append(c).append("\n"));
            throw new UnsupportedWithdrawOperationException(errorMessage.toString());
        }finally {
		    clearWithdrawMap();
        }

	}

	private WithdrawHandler getFirstHandler(List<WithdrawHandler> handlers) {
		return handlers.get(0);
	}

    private void checkIsCurrencyExist(Currency currency) {
        boolean isCurrencyExist = cellManager.getAvailableCurrencies().stream()
                .anyMatch(c -> c.equals(currency));
        if (!isCurrencyExist) {
            throw new UnsupportedWithdrawOperationException("The ATM doesn't support " + currency.getName());// TODO: 22.05.2017 Write message
        }
    }

    private void clearWithdrawMap() {
	    withdrawMap = new HashMap<>();
    }


}
