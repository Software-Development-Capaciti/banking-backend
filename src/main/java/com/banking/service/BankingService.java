package com.banking.service;

import com.banking.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BankingService {

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
        return Arrays.asList(
            new Transaction("Grocery Purchase", "2025-04-01", 75.30),
            new Transaction("Utility Bill", "2025-04-02", 120.00),
            new Transaction("Online Subscription", "2025-04-03", 9.99)
        );
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