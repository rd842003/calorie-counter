package edu.csu.caloriecounter.config;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.domain.MealType;
import edu.csu.caloriecounter.repo.LogEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.Random;

/**
 * Configuration class that seeds example LogEntry data into the application repository at startup.
 *
 * <p>The {@link #seed(LogEntryRepository)} method registers a {@link CommandLineRunner} bean that
 * inserts a few explicit meal entries for the current day and a series of deterministic daily totals
 * for the previous 14 days. This class is intended to provide a small dataset for local development
 * and demonstration purposes.
 *
 * <p>Patterns Used So Far:
 * - MVC (Spring): Controllers ↔ Service ↔ Views (Thymeleaf)
 * - Repository (DAO): JPA repository for LogEntry
 * - Singleton (via Spring): beans are container-scoped singletons
 * - Strategy (extension point): calculations centralized in LogService for later strategy injection
 */
@Configuration
public class DataLoader {
    /**
     * Creates a CommandLineRunner bean that seeds sample LogEntry objects into the provided repository.
     *
     * @param repo the repository used to persist LogEntry instances
     * @return a CommandLineRunner that inserts sample data on application startup
     */
    @Bean
    CommandLineRunner seed(LogEntryRepository repo) {
        return args -> {
            // Seed explicit example entries for the current day
            repo.save(new LogEntry(LocalDate.now(), "Fairlife Protein Shake", 150, 30, 6, 2, MealType.BREAKFAST));
            repo.save(new LogEntry(LocalDate.now(), "Banana", 105, 1, 27, 0, MealType.SNACKS));
            repo.save(new LogEntry(LocalDate.now(), "Chicken Breast (100g)", 165, 31, 0, 4, MealType.DINNER));

            // Deterministic pseudo-random daily totals for the previous 14 days to provide sample history
            Random r = new Random(42);
            for (int i = 1; i <= 14; i++) {
                LocalDate d = LocalDate.now().minusDays(i);
                int cal = 1800 + r.nextInt(600); // calories between 1800 and 2399
                int p = 120 + r.nextInt(60);     // protein grams between 120 and 179
                int c = 180 + r.nextInt(80);     // carbs grams between 180 and 259
                int f = 50 + r.nextInt(30);      // fat grams between 50 and 79
                repo.save(new LogEntry(d, "Daily total", cal, p, c, f, MealType.DINNER));
            }
        };
    }
}