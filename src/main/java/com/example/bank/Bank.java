package com.example.bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final Map<String, BankAccount> accounts = new HashMap<>();

    public BankAccount openAccount(String accountNumber, String ownerName, double openingBalance) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account already exists: " + accountNumber);
        }
        BankAccount account = new BankAccount(accountNumber, ownerName, openingBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    public BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("No such account: " + accountNumber);
        }
        return account;
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount from = getAccount(fromAccountNumber);
        BankAccount to = getAccount(toAccountNumber);
        from.withdraw(amount);
        to.deposit(amount);
    }

    public int accountCount() {
        return accounts.size();
    }
}
