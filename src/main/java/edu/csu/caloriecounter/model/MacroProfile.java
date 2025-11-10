package edu.csu.caloriecounter.model;

/**
 * Immutable macro nutrient profile in grams.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public record MacroProfile(double protein, double carbs, double fat) {
    public double totalCalories() {
        return protein * 4 + carbs * 4 + fat * 9;
    }
}