package org.example;

public class Sphere {
    private double radius;

    public Sphere(double radius) { this.radius = radius; }

    public double getVolume() {
        return (4 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    public double getSurfaceArea() {
        return 4 * Math.PI * Math.pow(radius, 2);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "radius=" + radius +
                '}';
    }
}
