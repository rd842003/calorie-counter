package edu.csu.caloriecounter.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Manages undo/redo stacks for commands.
 *
 * Pattern: Command.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class CommandManager {
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public void execute(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }

    public void undo() {
        if (!canUndo()) return;
        Command c = undoStack.pop();
        c.undo();
        redoStack.push(c);
    }

    public void redo() {
        if (!canRedo()) return;
        Command c = redoStack.pop();
        c.execute();
        undoStack.push(c);
    }
}