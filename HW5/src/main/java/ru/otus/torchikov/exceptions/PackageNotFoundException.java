package ru.otus.torchikov.exceptions;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 */
public class PackageNotFoundException extends RuntimeException {
    public PackageNotFoundException(String message) {
        super(message);
    }
}
