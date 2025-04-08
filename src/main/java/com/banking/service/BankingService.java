package com.banking.service;

import com.banking.model.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankingService {
    private static final Logger logger = LoggerFactory.getLogger(BankingService.class);
    
    private List<Transaction> transactions = new ArrayList<>();
    
    private static class Account {
        String accountType;
        String accountNumber;
        double balance;
        
        Account(String accountType, String accountNumber, double balance) {
            this.accountType = accountType;
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
    }
    
    private Map<String, Account> accounts = new HashMap<>();
    
    public BankingService() {
        // Initialize accounts with their numbers and balances
        accounts.put("current", new Account("current", "1234567890", 25000.00));
        accounts.put("savings", new Account("savings", "9876543210", 50000.00));
    }

    public DashboardData getDashboardData() {
        logger.debug("Getting dashboard data");
        List<Payment> payments = new ArrayList<>();
        Account current = accounts.get("current");
        Account savings = accounts.get("savings");
        return new DashboardData(current.balance, savings.balance, current.balance + savings.balance, payments);
    }

    public List<Payment> getPayments() {
        logger.debug("Getting payments list");
        return new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        logger.debug("Getting all transactions");
        return transactions;
    }

    public List<Transaction> getTransactionsByAccount(String accountType) {
        logger.debug("Getting transactions for account: {}", accountType);
        return transactions.stream()
            .filter(t -> t.getAccountType().equals(accountType))
            .collect(Collectors.toList());
    }

    public Transaction addTransaction(Transaction transaction) {
        logger.debug("Adding new transaction: {}", transaction);
        
        // Set the current date
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        transaction.setDate(currentDate);
        
        // Get source account
        Account sourceAccount = accounts.get(transaction.getAccountType());
        if (sourceAccount == null) {
            throw new RuntimeException("Invalid source account type: " + transaction.getAccountType());
        }

        // For payments, check if recipient account number matches any of our accounts
        if ("debit".equals(transaction.getType()) && transaction.getRecipientAccountNumber() != null) {
            // Find account by number
            Account recipientAccount = accounts.values().stream()
                .filter(acc -> acc.accountNumber.equals(transaction.getRecipientAccountNumber()))
                .findFirst()
                .orElse(null);

            if (recipientAccount != null) {
                // This is an internal transfer, create a credit transaction for the recipient
                Transaction creditTransaction = new Transaction(
                    "Received from " + sourceAccount.accountType,
                    currentDate,
                    transaction.getAmount(),
                    recipientAccount.accountType,
                    "credit",
                    null,
                    null,
                    null
                );
                transactions.add(creditTransaction);
                recipientAccount.balance += transaction.getAmount();
                logger.debug("Created internal transfer credit for account {}", recipientAccount.accountType);
            }
        }

        // Update source account balance
        if ("debit".equals(transaction.getType()) || "transfer".equals(transaction.getType())) {
            if (sourceAccount.balance >= transaction.getAmount()) {
                sourceAccount.balance -= transaction.getAmount();
            } else {
                throw new RuntimeException("Insufficient funds in " + sourceAccount.accountType + " Account");
            }
        } else if ("credit".equals(transaction.getType())) {
            sourceAccount.balance += transaction.getAmount();
        }
        
        // Add the main transaction
        transactions.add(transaction);
        logger.debug("Added transaction successfully. Current balance: {}, Savings balance: {}", 
            accounts.get("current").balance, accounts.get("savings").balance);

        // If it's a transfer, create a corresponding transaction for the receiving account
        if ("transfer".equals(transaction.getType())) {
            logger.debug("Creating corresponding transfer transaction for receiving account");
            
            Transaction receivingTransaction = new Transaction(
                "Received from " + transaction.getAccountType(),
                currentDate,
                transaction.getAmount(),
                transaction.getToAccount(), // The receiving account type
                "credit", // It's a credit for the receiving account
                null,
                null,
                null
            );
            transactions.add(receivingTransaction);
            logger.debug("Added receiving transaction: {}", receivingTransaction);
        }

        return transaction;
    }

    public List<Card> getCards() {
        logger.debug("Getting cards list");
        return Arrays.asList(
            new Card("Visa", "9850", "12/26"),
            new Card("Master", "2489", "12/26"),
            new Card("Amex", "6840", "12/26")
        );
    }

    public Profile getProfile() {
        logger.debug("Getting user profile");
        return new Profile("Andrew Forbist", "andrew@example.com");
    }
}