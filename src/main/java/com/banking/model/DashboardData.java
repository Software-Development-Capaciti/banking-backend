package com.banking.model;

import java.util.List;

public class DashboardData {
    private double creditLimit;
    private double spend;
    private double totalRevenue;
    private List<Payment> payments;

    // Constructors
    public DashboardData() {}

    public DashboardData(double creditLimit, double spend, double totalRevenue, List<Payment> payments) {
        this.creditLimit = creditLimit;
        this.spend = spend;
        this.totalRevenue = totalRevenue;
        this.payments = payments;
    }

    // Getters and Setters
    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}