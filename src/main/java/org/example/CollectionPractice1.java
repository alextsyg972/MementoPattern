package org.example;


import java.util.ArrayList;
import java.util.List;


public class CollectionPractice1 {
    public static void main(String[] args) {
        System.out.println(
                CollectionPractice1.filter(List.of(1, 2, 2), new FilterImpl<>(1, 2))
        );

    }


    public static <T> List<T> filter(List<T> array, Filter<T> filter) {
        List<T> arrayNew = new ArrayList<>();
        for (T t : array) {
            arrayNew.add(filter.apply(t));
        }
        return arrayNew;
    }

}