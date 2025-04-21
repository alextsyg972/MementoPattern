package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcurrentBank {

    private final Map<Integer, BankAccount> accounts = new HashMap<>();

    public BankAccount createAccount(int balance) {
        BankAccount account = new BankAccount(balance);
        accounts.put(account.getId(), account);
        return account;
    }

    public void transfer(BankAccount account1, BankAccount account2, int sum) {
        BankAccount first = account1.getId() < account2.getId() ? account1 : account2;
        BankAccount second = account1.getId() < account2.getId() ? account2 : account1;
        first.getLock().lock();
        second.getLock().lock();
        try {
            if (account1.withdraw(sum)) {
                account2.deposit(sum);
            }
        } finally {
            account2.getLock().unlock();
            account1.getLock().unlock();
        }
    }

    public int getTotalBalance() {
        List<BankAccount> accountList;

        accountList = new ArrayList<>(accounts.values());
        for (BankAccount account : accountList) {
            account.getLock().lock();
        }
        int total = 0;
        try {
            for (BankAccount account : accountList) {
                System.out.println(account);
                total += account.getBalance();
            }
            return total;
        } finally {
            for (BankAccount account : accountList) {
                account.getLock().unlock();
            }
        }
    }
}
