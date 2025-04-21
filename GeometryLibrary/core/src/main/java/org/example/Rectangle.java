package org.example;

public class Rectangle {

    private int a;
    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public double perimeter(double a, double b) {
        return 2 * (a + b);
    }

    public double square(double a, double b) {
        return a * b;
    }

}
