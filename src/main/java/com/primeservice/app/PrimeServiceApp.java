package com.primeservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Prime Service app.
 */
@SpringBootApplication
public class PrimeServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PrimeServiceController.class, args);
    }

}
