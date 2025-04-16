package org.example;

public class Main {
    public static void main(String[] args) {
        Caretaker caretaker = new Caretaker();

        caretaker.append("text");
        caretaker.append("text that need to undo");
        caretaker.undo();
    }
}