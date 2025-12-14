package com.fitplanhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FitPlanHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitPlanHubApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("FitPlanHub Backend is running on port 8080");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("===========================================\n");
    }
}
