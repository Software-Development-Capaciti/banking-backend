package com.banking.model;

public class Card {
    private String type;
    private String number;
    private String expiry;

    // Constructors
    public Card() {}

    public Card(String type, String number, String expiry) {
        this.type = type;
        this.number = number;
        this.expiry = expiry;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}