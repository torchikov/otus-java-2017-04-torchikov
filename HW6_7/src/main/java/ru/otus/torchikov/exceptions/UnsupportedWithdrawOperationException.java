package ru.otus.torchikov.exceptions;

/**
 * Created by Torchikov Sergei on 22.05.2017.
 * Exception for withdraw operations which can not be executed
 */
public class UnsupportedWithdrawOperationException extends RuntimeException {
	public UnsupportedWithdrawOperationException() {
	}
	public UnsupportedWithdrawOperationException(String message) {
		super(message);
	}
}
