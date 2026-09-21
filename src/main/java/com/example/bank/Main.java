package com.example.bank;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.openAccount("ACC001", "Alice", 1000.0);
        bank.openAccount("ACC002", "Bob", 500.0);

        System.out.println("== Simple Banking System Demo ==");

        bank.getAccount("ACC001").deposit(250.0);
        bank.getAccount("ACC002").withdraw(100.0);
        bank.transfer("ACC001", "ACC002", 300.0);

        BankAccount alice = bank.getAccount("ACC001");
        BankAccount bob = bank.getAccount("ACC002");

        System.out.println(alice.getOwnerName() + " (" + alice.getAccountNumber()
                + ") balance: " + alice.getBalance());
        System.out.println(bob.getOwnerName() + " (" + bob.getAccountNumber()
                + ") balance: " + bob.getBalance());

        System.out.println("\nAlice's transaction log:");
        alice.getTransactionLog().forEach(entry -> System.out.println("  " + entry));

        System.out.println("\nBuild verified. Total accounts: " + bank.accountCount());
    }
}
