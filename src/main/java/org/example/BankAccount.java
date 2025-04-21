package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private static final AtomicInteger ID = new AtomicInteger(1);
    private final int accountId;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(int initialBalance) {
        this.accountId = ID.getAndIncrement();
        this.balance = initialBalance;
    }

    public int getId() {
        return accountId;
    }

    public Lock getLock() {
        return lock;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            if (amount > 0) {
                balance += amount;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(int amount) {
        lock.lock();
        try {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
