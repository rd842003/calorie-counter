package edu.csu.caloriecounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application bootstrap for the Calorie Counter Spring Boot application.
 *
 * Starts the Spring container and initializes Spring MVC components (controllers, services, repositories).
 * This class is the entry point used by the executable JAR and by IDE runs.
 *
 * Patterns Used So Far:
 * - MVC (Spring): Controllers ↔ Service ↔ Views (Thymeleaf)
 * - Repository (DAO): JPA repository for LogEntry
 * - Singleton (via Spring): beans are container-scoped singletons
 * - Strategy (extension point): calculations centralized in LogService for later strategy injection
 */
@SpringBootApplication
public class Application {
    /**
     * Standard Spring Boot entry point.
     *
     * @param args runtime arguments forwarded to SpringApplication
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}