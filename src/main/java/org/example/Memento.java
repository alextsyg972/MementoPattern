package org.example;

public class Memento {

    private String text;

    public Memento(String savedText) {
        this.text = savedText;
    }

    public String getText() {
        return text;
    }

}
