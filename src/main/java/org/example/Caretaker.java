package org.example;

import java.util.*;

public class Caretaker {
    private Queue<Memento> snapshot;
    private Originator originator;
    public Caretaker() {
        this.snapshot = new ArrayDeque<>();
        originator = new Originator();
    }

    public void append(String text) {
        originator.append(text);
        snapshot.add(originator.save());
    }

    public void undo() {
        if (snapshot.peek() != null) {
            originator.undo(snapshot.peek());
        }
    }



}
