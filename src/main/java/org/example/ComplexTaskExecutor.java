package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ComplexTaskExecutor {

    private final int numberOfTasks;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    
    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        List<Integer> results = Collections.synchronizedList(new ArrayList<>());

        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("all completed");
            int sum = results.stream().mapToInt(Integer::intValue).sum();
            System.out.println("sum of values = " + sum);
        });

        for (int i = 0; i < numberOfTasks ; i++) {
            executorService.submit(new ComplexTask(i + 1, barrier, results));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Ожидание завершения пула потоков прервано.");
        }


    }

}
