package com.banking.controller;

import com.banking.model.*;
import com.banking.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/dashboard")
    public DashboardData getDashboardData() {
        return bankingService.getDashboardData();
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return bankingService.getPayments();
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return bankingService.getTransactions();
    }

    @GetMapping("/cards")
    public List<Card> getCards() {
        return bankingService.getCards();
    }

    @GetMapping("/profile")
    public Profile getProfile() {
        return bankingService.getProfile();
    }
}