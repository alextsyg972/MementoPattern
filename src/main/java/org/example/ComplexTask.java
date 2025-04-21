package org.example;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class ComplexTask implements Callable<Integer> {
    private final int taskId;
    private final CyclicBarrier barrier;

    public ComplexTask(int taskId, CyclicBarrier barrier) {
        this.taskId = taskId;
        this.barrier = barrier;
    }

    public Integer execute() {
        System.out.println(Thread.currentThread().getName() + " " + taskId + " выполняется...");
        Integer result = taskId * 2;
        System.out.println(Thread.currentThread().getName() + " " + result);
        return result;
    }

    @Override
    public Integer call() {
        Integer result = execute();
        try {
            barrier.await();
            return result;
        } catch (InterruptedException | BrokenBarrierException e) {
            return null;
        }
    }
}
