package edu.csu.caloriecounter.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * JPA entity representing a single logged entry of food or a daily total.
 *
 * Fields capture the date, a description of the entry, macronutrient totals (calories, protein,
 * carbs, fat) and the {@link MealType} classification for the entry.
 *
 * Instances are persisted via a JPA {@code Entity} annotation and use an auto-generated identity id.
 */
@Entity
public class LogEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Date for the log entry (local date). */
    private LocalDate date;

    /** Short description of the food item or entry (e.g., "Banana" or "Daily total"). */
    private String description;

    /** Total calories for this entry. */
    private int calories;

    /** Total protein grams for this entry. */
    private int protein;

    /** Total carbohydrate grams for this entry. */
    private int carbs;

    /** Total fat grams for this entry. */
    private int fat;

    /** Meal classification for this entry. Persisted as a string representation of the enum. */
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    /** No-args constructor required by JPA. */
    public LogEntry() {}

    /**
     * Convenience constructor for creating a LogEntry.
     *
     * @param date the entry date
     * @param description text description of the entry
     * @param calories calories value
     * @param protein protein grams
     * @param carbs carbohydrate grams
     * @param fat fat grams
     * @param mealType meal classification enum
     */
    public LogEntry(LocalDate date, String description, int calories, int protein, int carbs, int fat, MealType mealType) {
        this.date = date;
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.mealType = mealType;
    }

    /** @return the database id for this entry (may be null for transient instances). */
    public Long getId() { return id; }

    /** @return the date associated with this entry. */
    public LocalDate getDate() { return date; }

    /** @param date set the date for this entry. */
    public void setDate(LocalDate date) { this.date = date; }

    /** @return the description text for the entry. */
    public String getDescription() { return description; }

    /** @param description set the description text for the entry. */
    public void setDescription(String description) { this.description = description; }

    /** @return calories value for this entry. */
    public int getCalories() { return calories; }

    /** @param calories set the calories value for this entry. */
    public void setCalories(int calories) { this.calories = calories; }

    /** @return protein grams for this entry. */
    public int getProtein() { return protein; }

    /** @param protein set the protein grams for this entry. */
    public void setProtein(int protein) { this.protein = protein; }

    /** @return carbs grams for this entry. */
    public int getCarbs() { return carbs; }

    /** @param carbs set the carbohydrate grams for this entry. */
    public void setCarbs(int carbs) { this.carbs = carbs; }

    /** @return fat grams for this entry. */
    public int getFat() { return fat; }

    /** @param fat set the fat grams for this entry. */
    public void setFat(int fat) { this.fat = fat; }

    /** @return the MealType classification for this entry. */
    public MealType getMealType() { return mealType; }

    /** @param mealType set the MealType classification for this entry. */
    public void setMealType(MealType mealType) { this.mealType = mealType; }
}