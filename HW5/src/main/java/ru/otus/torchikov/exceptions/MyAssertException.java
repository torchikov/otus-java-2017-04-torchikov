package ru.otus.torchikov.exceptions;

import java.io.PrintStream;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 */
public class MyAssertException extends RuntimeException {
    public MyAssertException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(System.out);
    }
}
