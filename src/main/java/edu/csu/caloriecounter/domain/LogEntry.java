package edu.csu.caloriecounter.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class LogEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String description;
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    public LogEntry() {}
    public LogEntry(LocalDate date, String description, int calories, int protein, int carbs, int fat, MealType mealType) {
        this.date = date;
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.mealType = mealType;
    }
    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
    public int getProtein() { return protein; }
    public void setProtein(int protein) { this.protein = protein; }
    public int getCarbs() { return carbs; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public int getFat() { return fat; }
    public void setFat(int fat) { this.fat = fat; }
    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
}
