package edu.csu.caloriecounter.service;

import edu.csu.caloriecounter.domain.LogEntry;
import edu.csu.caloriecounter.repo.LogEntryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class LogService {
    private final LogEntryRepository repo;
    public LogService(LogEntryRepository repo) { this.repo = repo; }

    public List<LogEntry> todayEntries() { return repo.findByDate(LocalDate.now()); }

    public Map<String,Integer> todayTotals() { return totalsForDate(LocalDate.now()); }

    public Map<String,Integer> totalsForDate(LocalDate date) {
        List<LogEntry> list = repo.findByDate(date);
        int cal=0, p=0, c=0, f=0;
        for (LogEntry e: list) {
            cal += e.getCalories(); p += e.getProtein(); c += e.getCarbs(); f += e.getFat();
        }
        Map<String,Integer> m = new HashMap<>();
        m.put("calories", cal); m.put("protein", p); m.put("carbs", c); m.put("fat", f);
        return m;
    }

    public List<LogEntry> lastNDays(int days) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days-1);
        return repo.findByDateBetweenOrderByDateDesc(start, end);
    }

    public void addQuick(String desc, int calories, int protein, int carbs, int fat, String mealType) {
        edu.csu.caloriecounter.domain.MealType mt =
            edu.csu.caloriecounter.domain.MealType.valueOf(mealType);
        repo.save(new LogEntry(LocalDate.now(), desc, calories, protein, carbs, fat, mt));
    }
}
