package ru.otus.torchikov.exceptions;

/**
 * Created by sergei on 23.05.17.
 */
public class CurrencyUnavailableException extends RuntimeException{
    public CurrencyUnavailableException() {
    }

    public CurrencyUnavailableException(String message) {
        super(message);
    }
}
