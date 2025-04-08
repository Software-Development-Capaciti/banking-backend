package com.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BankingApplication {
    private static final Logger logger = LoggerFactory.getLogger(BankingApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Banking Application");
        SpringApplication.run(BankingApplication.class, args);
        logger.info("Banking Application started successfully");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        logger.debug("Configuring CORS");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                logger.debug("Adding CORS mappings");
                registry.addMapping("/api/**")
                    .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:5174",
                        "http://localhost:5175",
                        "http://127.0.0.1:64200",
                        "http://127.0.0.1:60281"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
                logger.debug("CORS configuration completed");
            }
        };
    }
}