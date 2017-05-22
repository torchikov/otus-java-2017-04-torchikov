package ru.otus.torchikov.withdrawal;


import ru.otus.torchikov.cells.Cell;
import ru.otus.torchikov.currency.Currency;
import ru.otus.torchikov.exceptions.UnsupportedWithdrawOperationException;
import ru.otus.torchikov.managers.WithdrawManager;

import java.util.Objects;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Handle withdraw operations
 */
public class WithdrawHandler {
    private WithdrawManager withdrawManager = WithdrawManager.getInstance();
    private WithdrawHandler next;
    private Cell cell;

    public WithdrawHandler(Cell cell) {
        this.cell = cell;
    }

    public void addNext(WithdrawHandler next) {
        if (this.next == null) {
            this.next = next;
        } else {
            next.addNext(next);
        }
    }

    public void executeWithdraw(Currency currency, long required) {
        long canWithdrawCount = Math.min(required / cell.getNominal().getNominalValue(), cell.getCount());
        if (canWithdrawCount < 1) {
            delegateToNext(currency, required);
        } else {
            long willWithdrawAmount = canWithdrawCount * cell.getNominal().getNominalValue();
            withdrawManager.addToWithdrawMap(cell, canWithdrawCount);
            delegateToNext(currency, required - willWithdrawAmount);
        }

    }

    private void delegateToNext(Currency currency, long required) {
        if (Objects.nonNull(next)) {
            next.executeWithdraw(currency, required);
        } else {
            if (required != 0) {
                throw new UnsupportedWithdrawOperationException("");
            }
        }
    }

    public Cell getCell() {
        return cell;
    }
}
