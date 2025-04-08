package com.banking.service;

import com.banking.model.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankingService {
    private static final Logger logger = LoggerFactory.getLogger(BankingService.class);
    
    private List<Transaction> transactions = new ArrayList<>();
    private double currentBalance = 25000.00;
    private double savingsBalance = 50000.00;

    public DashboardData getDashboardData() {
        logger.debug("Getting dashboard data");
        List<Payment> payments = new ArrayList<>();
        return new DashboardData(currentBalance, savingsBalance, currentBalance + savingsBalance, payments);
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
        
        // Update balances based on transaction type
        if ("current".equals(transaction.getAccountType())) {
            if ("debit".equals(transaction.getType()) || "transfer".equals(transaction.getType())) {
                if (currentBalance >= transaction.getAmount()) {
                    currentBalance -= transaction.getAmount();
                } else {
                    throw new RuntimeException("Insufficient funds in Current Account");
                }
            } else if ("credit".equals(transaction.getType())) {
                currentBalance += transaction.getAmount();
            }
        } else if ("savings".equals(transaction.getAccountType())) {
            if ("debit".equals(transaction.getType()) || "transfer".equals(transaction.getType())) {
                if (savingsBalance >= transaction.getAmount()) {
                    savingsBalance -= transaction.getAmount();
                } else {
                    throw new RuntimeException("Insufficient funds in Savings Account");
                }
            } else if ("credit".equals(transaction.getType())) {
                savingsBalance += transaction.getAmount();
            }
        }
        
        // Add the main transaction
        transactions.add(transaction);
        logger.debug("Added transaction successfully. Current balance: {}, Savings balance: {}", currentBalance, savingsBalance);

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