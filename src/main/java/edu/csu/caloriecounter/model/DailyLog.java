package edu.csu.caloriecounter.model;

import edu.csu.caloriecounter.observer.LogObserver;

import java.time.LocalDate;
import java.util.*;

/**
 * Holds entries for a date and notifies observers on changes.
 *
 * Pattern: Subject (Observer).
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class DailyLog {
    private final LocalDate date;
    private final Map<UUID, Entry> entries = new LinkedHashMap<>();
    private final List<LogObserver> observers = new ArrayList<>();
    private int goalCalories = 2000;

    public DailyLog(LocalDate date) { this.date = date; }

    public Entry add(Food food, MealType mealType) {
        Entry entry = new Entry(UUID.randomUUID(), food, mealType, date);
        entries.put(entry.getId(), entry);
        observers.forEach(o -> o.onEntryAdded(entry));
        return entry;
    }

    public void remove(UUID id) {
        Entry removed = entries.remove(id);
        if (removed != null) observers.forEach(o -> o.onEntryRemoved(removed));
    }

    public Collection<Entry> getEntries() { return Collections.unmodifiableCollection(entries.values()); }

    public void setGoalCalories(int goalCalories) {
        this.goalCalories = goalCalories;
        observers.forEach(o -> o.onGoalChanged(goalCalories));
    }
    public int getGoalCalories() { return goalCalories; }

    public void registerObserver(LogObserver o) { observers.add(o); }
    public void removeObserver(LogObserver o) { observers.remove(o); }
}