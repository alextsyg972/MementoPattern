package org.example;

public class Triangle {

    public static double perimeter(double a, double b, double c) {
        return a + b + c;
    }

    public static double square(double a, double b, double c) {
        double p = perimeter(a, b, c);
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}
