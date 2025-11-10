package edu.csu.caloriecounter.command;

/**
 * Reversible operation contract.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public interface Command {
    void execute();
    void undo();
}