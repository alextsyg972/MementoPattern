package org.example;

public class GeometryUtils {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static boolean areEqual(Rectangle s1, Rectangle s2) {
        return s1.perimeter(s1.getA(), s1.getB()) == s2.perimeter(s2.getA(), s2.getB());
    }




}