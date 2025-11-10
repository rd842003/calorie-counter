package edu.csu.caloriecounter.command;

import edu.csu.caloriecounter.model.DailyLog;
import edu.csu.caloriecounter.model.Entry;
import edu.csu.caloriecounter.model.Food;
import edu.csu.caloriecounter.model.MealType;

/**
 * Adds a food entry to a log; supports undo by removal.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class AddEntryCommand implements Command {
    private final DailyLog log;
    private final Food food;
    private final MealType mealType;
    private Entry created;

    public AddEntryCommand(DailyLog log, Food food, MealType mealType) {
        this.log = log;
        this.food = food;
        this.mealType = mealType;
    }

    @Override
    public void execute() { created = log.add(food, mealType); }

    @Override
    public void undo() {
        if (created != null) {
            log.remove(created.getId());
        }
    }
}