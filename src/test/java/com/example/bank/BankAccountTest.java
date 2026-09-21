package com.example.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC100", "Test User", 100.0);
    }

    @Test
    void newAccountHasOpeningBalance() {
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void depositIncreasesBalance() {
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void withdrawDecreasesBalance() {
        account.withdraw(40.0);
        assertEquals(60.0, account.getBalance());
    }

    @Test
    void withdrawMoreThanBalanceThrows() {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(1000.0));
    }

    @Test
    void depositNegativeAmountThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.0));
    }

    @Test
    void withdrawNegativeAmountThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10.0));
    }

    @Test
    void negativeOpeningBalanceThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount("ACC101", "Bad Open", -5.0));
    }

    @Test
    void transactionLogRecordsActivity() {
        account.deposit(10.0);
        account.withdraw(5.0);
        assertEquals(3, account.getTransactionLog().size()); // OPEN, DEPOSIT, WITHDRAW
    }
}
