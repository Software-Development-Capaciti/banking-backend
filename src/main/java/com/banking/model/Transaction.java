package com.banking.model;

public class Transaction {
    private String description;
    private String date;
    private double amount;
    private String accountType; // 'current' or 'savings'
    private String type; // 'PAY' or 'TRANSFER'
    private String toAccount; // For transfers
    private String recipientName; // For payments
    private String recipientAccountNumber; // For payments

    // Constructors
    public Transaction() {}

    public Transaction(String description, String date, double amount, String accountType, String type, String toAccount, String recipientName, String recipientAccountNumber) {
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.accountType = accountType;
        this.type = type;
        this.toAccount = toAccount;
        this.recipientName = recipientName;
        this.recipientAccountNumber = recipientAccountNumber;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }
}