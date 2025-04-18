package org.example;

import java.util.ArrayDeque;

import java.util.Queue;


public class MyQueue {
    private final Queue<Integer> queue;
    private final int limit;

    public MyQueue(int limit) {
        this.queue = new ArrayDeque<>();
        this.limit = limit;
    }

    public synchronized void enqueue(int e) {
        while (queue.size() == limit) {
            System.out.println("full");
            try {
                wait();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        queue.add(e);
        System.out.println("Element added: " + e);
        notify();
    }

    public synchronized int dequeue() {
        while (queue.isEmpty()) {
            System.out.println("empty, wait");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int poll = queue.remove();
        notify();
        System.out.println("remove " + poll);
        return poll;
    }


    public synchronized int size() {
        return queue.size();
    }

}
