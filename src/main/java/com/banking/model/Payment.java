package com.banking.model;

public class Payment {
    private String user;
    private String date;
    private double amount;

    // Constructors
    public Payment() {}

    public Payment(String user, String date, double amount) {
        this.user = user;
        this.date = date;
        this.amount = amount;
    }

    // Getters and Setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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