package com.example.bank;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private final String accountNumber;
    private final String ownerName;
    private double balance;
    private final List<String> transactionLog = new ArrayList<>();

    public BankAccount(String accountNumber, String ownerName, double openingBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance;
        log("OPEN", openingBalance);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        log("DEPOSIT", amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + " from balance " + balance);
        }
        balance -= amount;
        log("WITHDRAW", amount);
    }

    private void log(String type, double amount) {
        transactionLog.add(type + " " + amount + " -> balance " + balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionLog() {
        return List.copyOf(transactionLog);
    }
}
