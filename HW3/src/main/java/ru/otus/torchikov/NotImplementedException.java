package ru.otus.torchikov;

/**
 * Created by Torchikov Sergei on 15.04.2017.
 */
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("The method has not been implemented");
    }
}
