package org.example;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {

    private Long n;

    public FactorialTask(Long n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return 1L;
        }
        FactorialTask ft = new FactorialTask(n-1);
        ft.fork();
        return n * ft.join();
    }

}
