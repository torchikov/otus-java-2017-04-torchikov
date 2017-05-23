package ru.otus.torchikov.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergei on 23.05.17.
 */
public class StateCareTacker<T> {
    private List<State<T>> states = new ArrayList<>();

    public void addState(State<T> state) {
        states.add(state);
    }
    public State<T> getState() {
        return states.get(states.size() - 1); //Last state
    }
}
