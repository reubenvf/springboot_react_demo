package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ReubenVF: This is the entry point for the Spring Boot application.
// It bootstraps the application context and starts the web server.
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // ReubenVF: Launch the Spring Boot app and initialize all configured beans.
        SpringApplication.run(DemoApplication.class, args);
    }
}
