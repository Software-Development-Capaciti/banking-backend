package com.banking.service;

import com.banking.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankingService {
    private List<Transaction> transactions = new ArrayList<>(Arrays.asList(
        new Transaction("Grocery Purchase", "2025-04-01", 75.30, "current", "PAY", null, "FreshMart", "1234567890"),
        new Transaction("Utility Bill", "2025-04-02", 120.00, "current", "PAY", null, "City Power", "9876543210"),
        new Transaction("Online Subscription", "2025-04-03", 9.99, "current", "PAY", null, "Netflix", "5432167890"),
        new Transaction("Transfer to Savings", "2025-04-03", 500.00, "current", "TRANSFER", "savings", null, null),
        new Transaction("Received from Current", "2025-04-03", 500.00, "savings", "TRANSFER", null, null, null),
        new Transaction("Investment Deposit", "2025-04-04", 1000.00, "savings", "PAY", null, "Investment Fund", "6789054321")
    ));

    public DashboardData getDashboardData() {
        List<Payment> payments = Arrays.asList(
            new Payment("Nursing Assistant", "3 days ago", 446.61),
            new Payment("Web Designer", "22 hours ago", 202.87),
            new Payment("Dog Trainer", "2 hours ago", 106.58),
            new Payment("Marketing Coordinator", "3 days ago", 943.85)
        );
        return new DashboardData(2500.00, 500.00, 8456.00, payments);
    }

    public List<Payment> getPayments() {
        return Arrays.asList(
            new Payment("Nursing Assistant", "3 days ago", 446.61),
            new Payment("Web Designer", "22 hours ago", 202.87),
            new Payment("Dog Trainer", "2 hours ago", 106.58),
            new Payment("Marketing Coordinator", "3 days ago", 943.85)
        );
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getTransactionsByAccount(String accountType) {
        return transactions.stream()
            .filter(t -> t.getAccountType().equals(accountType))
            .collect(Collectors.toList());
    }

    public Transaction addTransaction(Transaction transaction) {
        // Set the current date
        transaction.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        
        // Add the transaction
        transactions.add(transaction);

        // If it's a transfer, create a corresponding transaction for the receiving account
        if ("TRANSFER".equals(transaction.getType())) {
            Transaction receivingTransaction = new Transaction(
                "Received from " + transaction.getAccountType(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getToAccount(),
                "TRANSFER",
                null,
                null,
                null
            );
            transactions.add(receivingTransaction);
        }

        return transaction;
    }

    public List<Card> getCards() {
        return Arrays.asList(
            new Card("Visa", "9850", "12/26"),
            new Card("Master", "2489", "12/26"),
            new Card("Amex", "6840", "12/26")
        );
    }

    public Profile getProfile() {
        return new Profile("Andrew Forbist", "andrew@example.com");
    }
}