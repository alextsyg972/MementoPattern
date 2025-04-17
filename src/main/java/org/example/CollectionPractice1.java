package org.example;


import java.util.Arrays;

public class CollectionPractice1 {
    public static void main(String[] args) {
        System.out.println(
                Arrays.toString(
                        CollectionPractice1.filter(new String[]{"da", "net"}, o -> o + " filter")
                )
        );
        CollectionPractice1.filter(new Integer[]{1, 2, 3}, o -> o + 2);

    }

    public static <T> T[] filter(T[] t, Filter<T> filter) {
        Object[] ts = new Object[t.length];
        for (int i = 0; i <t.length ; i++) {
            ts[i] = filter.apply(t[i]);
        }
        return (T[]) ts;
    }


}