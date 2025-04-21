package org.example;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ComplexTask implements Runnable {
    private final int taskId;
    private final CyclicBarrier barrier;
    private final List<Integer> results;

    public ComplexTask(int taskId, CyclicBarrier barrier, List<Integer> results) {
        this.taskId = taskId;
        this.barrier = barrier;
        this.results = results;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " " + taskId + " выполняется...");
        Integer result = taskId * 2;
        System.out.println(Thread.currentThread().getName() + " " + result);
        results.add(result);
    }

    @Override
    public void run() {
        execute();
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.err.println("Ошибка синхронизации задачи #" + taskId);
        }
    }
}
