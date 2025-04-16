package org.example;

public class Main {
    public static void main(String[] args) {
        Originator stringOriginator = new Originator();
        Caretaker caretaker = new Caretaker();
        stringOriginator.append("Hello");

        caretaker.saveState(stringOriginator.save());
        stringOriginator.append(" World");

        Memento memento = caretaker.restoreState();
        if (memento != null) {
            stringOriginator.undo(memento);
        }

    }
}