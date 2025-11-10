package edu.csu.caloriecounter.settings;

/**
 * Singleton settings manager.
 *
 * Design Pattern:
 * - Singleton: ensures a single source of truth for user preferences.
 *
 * Responsibilities:
 * - Load/save goal calories, unit system, and last Excel path
 *
 * Thread-safety: lazy double-checked locking for instance creation.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public final class SettingsManager {
    private static volatile SettingsManager INSTANCE;

    private int dailyGoalCalories = 2000;
    private String unitSystem = "METRIC";
    private String lastFilePath = "data/calorie-data.xlsx";

    private SettingsManager() {}

    public static SettingsManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SettingsManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SettingsManager();
                }
            }
        }
        return INSTANCE;
    }

    public void load() {
        // TODO: Load from properties or Excel Users sheet
    }

    public void persist() {
        // TODO: Save to properties or Excel Users sheet
    }

    public int getDailyGoalCalories() { return dailyGoalCalories; }
    public void setDailyGoalCalories(int calories) { this.dailyGoalCalories = calories; }

    public String getUnitSystem() { return unitSystem; }
    public void setUnitSystem(String unitSystem) { this.unitSystem = unitSystem; }

    public String getLastFilePath() { return lastFilePath; }
    public void setLastFilePath(String lastFilePath) { this.lastFilePath = lastFilePath; }
}