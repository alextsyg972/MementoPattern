package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionPractice2 {
    public static void main(String[] args) {
        System.out.println(
                CollectionPractice2.countOfElements(List.of("one", "two", "one", "three", "four", "two"))
        );
    }

    public static <T> Map<T, Integer> countOfElements(List<T> list) {
        Map<T, Integer> map = new HashMap<>();
        for (T list1 : list) {
            map.put(list1, map.getOrDefault(list1, 0) + 1);
        }
        return map;
    }
}
