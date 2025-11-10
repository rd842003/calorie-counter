package edu.csu.caloriecounter.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A single log entry associating a food with a date and meal type.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class Entry {
    private final UUID id;
    private final Food food;
    private final MealType mealType;
    private final LocalDate date;
    private String notes;

    public Entry(UUID id, Food food, MealType mealType, LocalDate date) {
        this.id = id;
        this.food = food;
        this.mealType = mealType;
        this.date = date;
    }

    public UUID getId() { return id; }
    public Food getFood() { return food; }
    public MealType getMealType() { return mealType; }
    public LocalDate getDate() { return date; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}