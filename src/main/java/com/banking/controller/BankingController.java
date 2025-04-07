package com.banking.controller;

import com.banking.model.*;
import com.banking.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend requests
public class BankingController {
    private static final Logger logger = LoggerFactory.getLogger(BankingController.class);

    @Autowired
    private BankingService bankingService;

    @GetMapping("/dashboard")
    public DashboardData getDashboardData() {
        logger.debug("Getting dashboard data");
        return bankingService.getDashboardData();
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        logger.debug("Getting payments");
        return bankingService.getPayments();
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        logger.debug("Getting all transactions");
        return bankingService.getTransactions();
    }

    @GetMapping("/transactions/{accountType}")
    public List<Transaction> getTransactionsByAccount(@PathVariable String accountType) {
        logger.debug("Getting transactions for account type: {}", accountType);
        return bankingService.getTransactionsByAccount(accountType);
    }

    @PostMapping("/transactions/pay")
    public Transaction pay(@RequestBody Transaction transaction) {
        logger.debug("Processing payment: {}", transaction);
        try {
            Transaction result = bankingService.addTransaction(transaction);
            logger.debug("Payment processed successfully: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error processing payment: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/transactions/transfer")
    public Transaction transfer(@RequestBody Transaction transaction) {
        logger.debug("Processing transfer: {}", transaction);
        try {
            Transaction result = bankingService.addTransaction(transaction);
            logger.debug("Transfer processed successfully: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error processing transfer: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/cards")
    public List<Card> getCards() {
        logger.debug("Getting cards");
        return bankingService.getCards();
    }

    @GetMapping("/profile")
    public Profile getProfile() {
        logger.debug("Getting profile");
        return bankingService.getProfile();
    }
}