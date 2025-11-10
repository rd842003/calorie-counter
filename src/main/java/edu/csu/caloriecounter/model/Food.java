package edu.csu.caloriecounter.model;

import java.util.Objects;

/**
 * Represents a single food item.
 *
 * Pattern: Leaf (Composite) when aggregated within Meal.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class Food {
    private final String name;
    private final double servingSize; // grams
    private final double calories;
    private final MacroProfile macros;

    public Food(String name, double servingSize, double calories, MacroProfile macros) {
        this.name = Objects.requireNonNull(name, "name");
        this.servingSize = servingSize;
        this.calories = calories;
        this.macros = macros;
    }

    public String getName() { return name; }
    public double getServingSize() { return servingSize; }
    public double getCalories() { return calories; }
    public MacroProfile getMacros() { return macros; }
}