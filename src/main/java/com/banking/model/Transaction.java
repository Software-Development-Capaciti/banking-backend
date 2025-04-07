package com.banking.model;

public class Transaction {
    private String description;
    private String date;
    private double amount;

    // Constructors
    public Transaction() {}

    public Transaction(String description, String date, double amount) {
        this.description = description;
        this.date = date;
        this.amount = amount;
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
}