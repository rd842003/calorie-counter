package edu.csu.caloriecounter.domain;

/**
 * Enumeration of meal classifications used to categorize log entries.
 *
 * <p>Values are persisted as their String names in the database (see {@link jakarta.persistence.EnumType#STRING} usage).
 */
public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACKS,
    OTHER
}
