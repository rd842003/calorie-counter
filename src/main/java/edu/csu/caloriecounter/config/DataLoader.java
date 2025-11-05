package edu.csu.caloriecounter.config;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.domain.MealType;
import edu.csu.caloriecounter.repo.LogEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.Random;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner seed(LogEntryRepository repo) {
        return args -> {
            repo.save(new LogEntry(LocalDate.now(), "Fairlife Protein Shake", 150, 30, 6, 2, MealType.BREAKFAST));
            repo.save(new LogEntry(LocalDate.now(), "Banana", 105, 1, 27, 0, MealType.SNACKS));
            repo.save(new LogEntry(LocalDate.now(), "Chicken Breast (100g)", 165, 31, 0, 4, MealType.DINNER));
            Random r = new Random(42);
            for (int i=1;i<=14;i++) {
                LocalDate d = LocalDate.now().minusDays(i);
                int cal = 1800 + r.nextInt(600);
                int p = 120 + r.nextInt(60);
                int c = 180 + r.nextInt(80);
                int f = 50 + r.nextInt(30);
                repo.save(new LogEntry(d, "Daily total", cal, p, c, f, MealType.DINNER));
            }
        };
    }
}
