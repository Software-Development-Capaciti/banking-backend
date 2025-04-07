package com.banking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BankingApplication {
    private static final Logger logger = LoggerFactory.getLogger(BankingApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Banking Application...");
        try {
            SpringApplication.run(BankingApplication.class, args);
            logger.info("Banking Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start Banking Application: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                logger.debug("Configuring CORS...");
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
                logger.debug("CORS configuration completed");
            }
        };
    }
}