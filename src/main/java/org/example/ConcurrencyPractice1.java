package org.example;


public class ConcurrencyPractice1 {
    public static void main(String[] args) {

        MyQueue queue = new MyQueue(5);
        new Thread(() -> queue.dequeue()).start();
        new Thread(() -> queue.enqueue(2)).start();
        new Thread(() -> queue.enqueue(32)).start();
        new Thread(() -> queue.enqueue(15)).start();
        new Thread(() -> queue.enqueue(27)).start();
        new Thread(() -> queue.enqueue(17)).start();
        new Thread(() -> queue.enqueue(21)).start();
        new Thread(() -> queue.dequeue()).start();
        new Thread(() -> queue.dequeue()).start();
        new Thread(() -> queue.dequeue()).start();
        new Thread(() -> queue.dequeue()).start();
        new Thread(() -> queue.dequeue()).start();
    }
}