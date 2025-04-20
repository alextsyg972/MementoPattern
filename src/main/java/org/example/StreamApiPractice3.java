package org.example;

import java.util.concurrent.ForkJoinPool;

public class StreamApiPractice3 {
    public static void main(String[] args) {

        long n = 10; // Вычисление факториала для числа 10

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);

        long result = forkJoinPool.invoke(factorialTask);
        System.out.println("Факториал " + n + "! = " + result);


    }
}