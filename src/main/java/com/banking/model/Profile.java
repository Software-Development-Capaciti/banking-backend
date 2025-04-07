package com.banking.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Profile {
    private static final Logger logger = LoggerFactory.getLogger(Profile.class);
    private String name;
    private String email;

    // Constructors
    public Profile() {
        logger.debug("Creating empty Profile");
    }

    public Profile(String name, String email) {
        logger.debug("Creating Profile with name: {} and email: {}", name, email);
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        logger.debug("Getting name: {}", name);
        return name;
    }

    public void setName(String name) {
        logger.debug("Setting name to: {}", name);
        this.name = name;
    }

    public String getEmail() {
        logger.debug("Getting email: {}", email);
        return email;
    }

    public void setEmail(String email) {
        logger.debug("Setting email to: {}", email);
        this.email = email;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}