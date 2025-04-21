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
        account1.getLock().lock();
        account2.getLock().lock();
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

        synchronized (this) {
            accountList = new ArrayList<>(accounts.values());
        }

        int total = 0;
        for (BankAccount account : accountList) {
            System.out.println(account);
            total += account.getBalance();
        }
        return total;
    }

}
