package ru.otus.torchikov.exceptions;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 */
public class UniqueMethodConstraintException extends RuntimeException {
    public UniqueMethodConstraintException(String message) {
        super(message);
    }
}
