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
        // Initialize accounts with their numbers and zero balances
        accounts.put("current", new Account("current", "1234567890", 0.00));
        accounts.put("savings", new Account("savings", "9876543210", 0.00));
    }

    public DashboardData getDashboardData() {
        logger.debug("Getting dashboard data");
        List<Payment> payments = new ArrayList<>();
        Account current = accounts.get("current");
        Account savings = accounts.get("savings");
        
        logger.debug("Current balance: {}, Savings balance: {}, Total balance: {}", 
            current.balance, savings.balance, current.balance + savings.balance);
            
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

        // Handle different transaction types
        if ("deposit".equals(transaction.getType())) {
            // Add money to account
            sourceAccount.balance += transaction.getAmount();
            
            // Set description if not provided
            if (transaction.getDescription() == null || transaction.getDescription().trim().isEmpty()) {
                transaction.setDescription("Money In - Cash Deposit");
            } else {
                transaction.setDescription("Money In - " + transaction.getDescription());
            }
            
            // Set the new balance in the transaction
            transaction.setBalance(sourceAccount.balance);
            transactions.add(transaction);
            
            logger.debug("Deposit completed. New balance: {}", sourceAccount.balance);
        } else if ("transfer".equals(transaction.getType())) {
            // Get destination account
            Account destAccount = accounts.get(transaction.getToAccount());
            if (destAccount == null) {
                throw new RuntimeException("Invalid destination account type: " + transaction.getToAccount());
            }

            // Check if we have enough balance
            if (sourceAccount.balance < transaction.getAmount()) {
                throw new RuntimeException("Insufficient funds in " + sourceAccount.accountType + " Account");
            }

            // Update balances
            sourceAccount.balance -= transaction.getAmount();
            destAccount.balance += transaction.getAmount();

            // Set the new balance in the transaction
            transaction.setBalance(sourceAccount.balance);
            transactions.add(transaction);

            // Add the credit transaction to destination
            Transaction creditTransaction = new Transaction(
                generateTransactionId(),
                "Received from " + sourceAccount.accountType,
                currentDate,
                transaction.getAmount(),
                destAccount.accountType,
                "credit",
                null,
                null,
                null
            );
            creditTransaction.setBalance(destAccount.balance);
            transactions.add(creditTransaction);

            logger.debug("Transfer completed. Source balance: {}, Destination balance: {}", 
                sourceAccount.balance, destAccount.balance);

        } else if ("debit".equals(transaction.getType())) {
            // Check if we have enough balance
            if (sourceAccount.balance < transaction.getAmount()) {
                throw new RuntimeException("Insufficient funds in " + sourceAccount.accountType + " Account");
            }

            // Handle payments
            sourceAccount.balance -= transaction.getAmount();
            transaction.setBalance(sourceAccount.balance);
            transactions.add(transaction);

            // If it's an internal payment (recipient is one of our accounts)
            if (transaction.getRecipientAccountNumber() != null) {
                Account recipientAccount = accounts.values().stream()
                    .filter(acc -> acc.accountNumber.equals(transaction.getRecipientAccountNumber()))
                    .findFirst()
                    .orElse(null);

                if (recipientAccount != null) {
                    // Add money to recipient account
                    recipientAccount.balance += transaction.getAmount();

                    // Create credit transaction for recipient
                    Transaction creditTransaction = new Transaction(
                        generateTransactionId(),
                        "Received payment from " + sourceAccount.accountType,
                        currentDate,
                        transaction.getAmount(),
                        recipientAccount.accountType,
                        "credit",
                        null,
                        transaction.getRecipientName(),
                        transaction.getRecipientAccountNumber()
                    );
                    creditTransaction.setBalance(recipientAccount.balance);
                    transactions.add(creditTransaction);
                }
            }

            logger.debug("Payment completed. New balance: {}", sourceAccount.balance);
        }

        logger.debug("Account balances after transaction - Current: {}, Savings: {}", 
            accounts.get("current").balance, accounts.get("savings").balance);
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
    
    public void deleteTransaction(String id) {
        logger.debug("Deleting transaction with ID: {}", id);
        
        // Find the transaction to delete
        Transaction transactionToDelete = transactions.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
        
        // Get the account associated with this transaction
        Account account = accounts.get(transactionToDelete.getAccountType());
        
        // Update account balance based on transaction type
        if ("deposit".equals(transactionToDelete.getType()) || "credit".equals(transactionToDelete.getType())) {
            // If it was a deposit or credit, subtract the amount from the balance
            account.balance -= transactionToDelete.getAmount();
            logger.debug("Subtracted {} from {} account. New balance: {}", 
                transactionToDelete.getAmount(), account.accountType, account.balance);
        } else if ("debit".equals(transactionToDelete.getType()) || "transfer".equals(transactionToDelete.getType())) {
            // If it was a payment or transfer out, add the amount back to the balance
            account.balance += transactionToDelete.getAmount();
            logger.debug("Added {} back to {} account. New balance: {}", 
                transactionToDelete.getAmount(), account.accountType, account.balance);
        }
        
        // Remove the transaction
        transactions.remove(transactionToDelete);
        logger.debug("Transaction deleted successfully. Current transaction count: {}", transactions.size());
    }
    
    private String generateTransactionId() {
        // This method should be implemented to generate a unique transaction ID
        // For now, it just returns a dummy ID
        return "TRANSACTION_ID";
    }
}