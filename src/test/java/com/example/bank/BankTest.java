package com.example.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.openAccount("ACC001", "Alice", 1000.0);
        bank.openAccount("ACC002", "Bob", 500.0);
    }

    @Test
    void opensAccountsCorrectly() {
        assertEquals(2, bank.accountCount());
    }

    @Test
    void duplicateAccountNumberThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> bank.openAccount("ACC001", "Someone Else", 0.0));
    }

    @Test
    void lookupMissingAccountThrows() {
        assertThrows(IllegalArgumentException.class, () -> bank.getAccount("NOPE"));
    }

    @Test
    void transferMovesMoneyBetweenAccounts() {
        bank.transfer("ACC001", "ACC002", 200.0);
        assertEquals(800.0, bank.getAccount("ACC001").getBalance());
        assertEquals(700.0, bank.getAccount("ACC002").getBalance());
    }

    @Test
    void transferFailsWhenSenderHasInsufficientFunds() {
        assertThrows(InsufficientFundsException.class,
                () -> bank.transfer("ACC002", "ACC001", 999999.0));
    }
}
