package edu.csu.caloriecounter.service;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.repo.LogEntryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

/**
 * Service layer responsible for retrieving and aggregating LogEntry data.
 *
 * <p>This class centralizes calculation logic (totals, ranges) so controllers remain thin and focus
 * on request/response handling. This centralization also serves as an extension point for applying
 * alternate calculation strategies in the future.
 *
 * <p>Patterns Used So Far:
 * - MVC (Spring): Controllers ↔ Service ↔ Views (Thymeleaf)
 * - Repository (DAO): JPA repository for LogEntry
 * - Singleton (via Spring): this service is a container-scoped singleton
 * - Strategy (extension point): calculations centralized here for future strategy injection
 */
@Service
public class LogService {
    private final LogEntryRepository repo;

    /**
     * Constructor injection of the LogEntryRepository.
     *
     * @param repo repository used for persistence operations
     */
    public LogService(LogEntryRepository repo) { this.repo = repo; }

    /**
     * Retrieve the list of entries for today.
     *
     * @return list of {@link LogEntry} for the current date
     */
    public List<LogEntry> todayEntries() { return repo.findByDate(LocalDate.now()); }

    /**
     * Compute totals (calories, protein, carbs, fat) for today.
     *
     * @return map keyed by nutrient name ("calories","protein","carbs","fat") to integer totals
     */
    public Map<String,Integer> todayTotals() { return totalsForDate(LocalDate.now()); }

    /**
     * Compute totals for a specific date by aggregating values from all entries for that date.
     *
     * @param date the date to aggregate totals for
     * @return map keyed by nutrient name to integer totals
     */
    public Map<String,Integer> totalsForDate(LocalDate date) {
        List<LogEntry> list = repo.findByDate(date);
        int cal=0, p=0, c=0, f=0;
        for (LogEntry e: list) {
            cal += e.getCalories();
            p += e.getProtein();
            c += e.getCarbs();
            f += e.getFat();
        }
        Map<String,Integer> m = new HashMap<>();
        m.put("calories", cal);
        m.put("protein", p);
        m.put("carbs", c);
        m.put("fat", f);
        return m;
    }

    /**
     * Retrieve entries for the last N days (inclusive of today).
     *
     * @param days number of days to include (must be >= 1)
     * @return list of entries from start..end ordered by date descending
     */
    public List<LogEntry> lastNDays(int days) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days-1);
        return repo.findByDateBetweenOrderByDateDesc(start, end);
    }

    /**
     * Helper to add a quick entry for today. Accepts the meal type name and converts it to the enum.
     *
     * @param desc description for the new entry
     * @param calories calories value
     * @param protein protein grams
     * @param carbs carbohydrate grams
     * @param fat fat grams
     * @param mealType string name of the MealType enum
     */
    public void addQuick(String desc, int calories, int protein, int carbs, int fat, String mealType) {
        edu.csu.caloriecounter.domain.MealType mt =
            edu.csu.caloriecounter.domain.MealType.valueOf(mealType);
        repo.save(new LogEntry(LocalDate.now(), desc, calories, protein, carbs, fat, mt));
    }
}
