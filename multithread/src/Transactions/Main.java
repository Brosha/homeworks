package Transactions;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Main {
    static class Transaction {
        final int fromId;
        final int toId;
        final int amount;

        Transaction(int fromId, int toId, int amount) {
            this.fromId = fromId;
            this.toId = toId;
            this.amount = amount;
        }

        public static void main(String[] args) {
            // Ваше решение

            Scanner scanner = new Scanner(System.in);

            int usersCount = scanner.nextInt();
            scanner.nextLine();
            String inputLine = scanner.nextLine();
            String[] input = inputLine.split(" ");
            int[] balances = new int[usersCount];

            for (int i = 0; i < usersCount; i++) {
                balances[i] = Integer.valueOf(input[i]);
            }

            int transactionsCount = scanner.nextInt();
            scanner.nextLine();
            Transaction[] transactions = new Transaction[transactionsCount];
            for (int i = 0; i < transactionsCount; i++) {
                inputLine = scanner.nextLine();
                input = inputLine.split(" - ");
                transactions[i] = new Transaction(Integer.valueOf(input[0]), Integer.valueOf(input[2]), Integer.valueOf(input[1]));

            }
            for (int i = 0; i < usersCount; i++) {
                System.out.println("User " + i + " balance before transactions: " + balances[i]);
            }
            for (int i = 0; i < transactionsCount; i++) {
                System.out.println("Transaction " + i + ": " + transactions[i].fromId + " - " + transactions[i].amount + " - " + transactions[i].toId);
            }

            ExecutorService executorService = Executors.newCachedThreadPool();
            AtomicIntegerArray atomicBalances = new AtomicIntegerArray(balances);
            for (Transaction t : transactions) {
                executorService.submit(new TransactionThread(t, atomicBalances
                ));
            }

            executorService.shutdown();
            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < usersCount; i++) {
                System.out.println("User " + i + " final balance: " + atomicBalances.get(i));
            }

        }
    }

    static class TransactionThread implements Runnable {
        Transaction transaction;
        private final AtomicIntegerArray balances;

        TransactionThread(Transaction transaction, AtomicIntegerArray balances) {
            this.transaction = transaction;
            this.balances = balances;
        }

        @Override
        public void run() {
            transfer(transaction);

        }

        public void transfer(Transaction transaction) {
            while (true) {
                int fromBalance = balances.get(transaction.fromId);
                if (fromBalance < transaction.amount) {
                    System.out.println(Thread.currentThread().getName() + " попытался перевести " + transaction.amount + ", но недостаточно средств.");
                    return;
                }

                if (balances.compareAndSet(transaction.fromId, fromBalance, fromBalance - transaction.amount)) {
                    balances.addAndGet(transaction.toId, transaction.amount);
                    System.out.println(Thread.currentThread().getName() + " перевёл " + transaction.amount);
                    return;
                }
            }
        }

    }
}


