package edu.csu.caloriecounter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite representing a Meal grouping Foods and/or nested Meals.
 *
 * Pattern: Composite – aggregates calories/macros seamlessly.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class Meal {
    private final MealType type;
    private final List<Object> children = new ArrayList<>(); // Food or Meal

    public Meal(MealType type) {
        this.type = type;
    }

    public void add(Object component) { children.add(component); }
    public void remove(Object component) { children.remove(component); }

    public double getTotalCalories() {
        return children.stream()
            .mapToDouble(c -> {
                if (c instanceof Food f) return f.getCalories();
                if (c instanceof Meal m) return m.getTotalCalories();
                return 0.0;
            }).sum();
    }

    public MealType getType() { return type; }
    public List<Object> getChildren() { return List.copyOf(children); }
}