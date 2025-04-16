package org.example;

public class Originator {

    private StringBuilder text;

    public Originator() {
        this.text = new StringBuilder();
    }

    public StringBuilder append(String str) {
        return text.append(str);
    }

    public StringBuilder delete(int start, int end) {
        return text.delete(start,end);
    }

    public Memento save() {
        return new Memento(text.toString());
    }

    public void undo(Memento memento) {
        text = new StringBuilder(memento.getText());
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "text=" + text +
                '}';
    }
}
