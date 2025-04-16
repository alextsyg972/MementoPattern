package org.example;

import java.util.*;

public class Caretaker {
    private Queue<Memento> snapshot;
    public Caretaker() {
        this.snapshot = new ArrayDeque<>();
    }
    public void saveState(Memento memento) {
        snapshot.add(memento);
    }
    public Memento restoreState() {
        if (!snapshot.isEmpty()) {
            return snapshot.poll();
        }
        return null;
    }
}
