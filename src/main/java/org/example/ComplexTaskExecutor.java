package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ComplexTaskExecutor {

    private final int numberOfTasks;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }


    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(this.numberOfTasks);
        List<Future<Integer>> futures = new ArrayList<>();

        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println(Thread.currentThread().getName() + " Барьер");
        });

        for (int i = 0; i < numberOfTasks; i++) {
            futures.add(executorService.submit(new ComplexTask(i + 1, barrier)));
        }
        executorService.shutdown();

        List<Integer> results = futures.stream().map(x -> {
            try {
                return x.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        int result = results.stream().mapToInt(Integer::intValue).sum();
        System.out.println(Thread.currentThread().getName() + " - Результат задач: " + result);

    }

}
