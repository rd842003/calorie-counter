package edu.csu.caloriecounter.observer;

import edu.csu.caloriecounter.model.Entry;

/**
 * Observer for log changes.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public interface LogObserver {
    void onEntryAdded(Entry entry);
    void onEntryRemoved(Entry entry);
    void onGoalChanged(int newGoalCalories);
}