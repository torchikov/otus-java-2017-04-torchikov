package ru.otus.torchikov.state;

/**
 * Created by sergei on 23.05.17.
 */
public interface State<T> {
    T getState();

    void setState(T state);

}
