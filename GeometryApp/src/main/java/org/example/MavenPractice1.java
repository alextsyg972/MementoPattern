package org.example;

public class MavenPractice1 {
    public static void main(String[] args) {
        Triangle.perimeter(10,3,4);
        Triangle.square(11.2,33,12.3);

        Circle.perimeter(4);
        Circle.square(32);

        Rectangle rectangle1 = new Rectangle();
        Rectangle rectangle2 = new Rectangle();

        rectangle1.setA(1);
        rectangle1.setB(2);

        rectangle2.setA(3);
        rectangle2.setB(3);

        System.out.println(GeometryUtils.areEqual(rectangle1, rectangle2));

        Cube cube = new Cube(3);
        System.out.println("Cube volume: " + cube.getVolume());

        Sphere sphere = new Sphere(2);
        System.out.println("Sphere surface area: " + sphere.getSurfaceArea());

    }
}