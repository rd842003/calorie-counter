# Calorie Counter

Team: Robert Daniel, Mekaila Cummings, Erik Jasso

## Project Summary
The Calorie Counter App is a Java 17 desktop application that helps users track daily food intake and monitor calories without requiring a database. It uses JavaFX for its graphical user interface and Apache POI for Excel-based data storage, making it portable and easy to maintain.

## Main Goals
1. Allow users to log foods manually or import them from Excel.
2. Group foods into meals (Breakfast/Lunch/Dinner) and compute totals automatically.
3. Display daily calorie totals and progress toward a goal.
4. Support undo/redo for add/remove actions.
5. Export daily reports in multiple formats (CSV/JSON).

## Functional Requirements
- Track foods, meals, calories, and macros.
- Persist data in Excel sheets (Users, Foods, Entries, Goals).
- Use Observer pattern to update dashboard automatically.
- Use Factory to create food objects from Excel rows.
- Use Command to implement undo/redo for entries.
- Use Singleton for global Settings Manager.
- Ensure execution on Windows without extra installation.

## Non-Functional Requirements
- Portable: uses Excel storage instead of database.
- Modular, pattern-driven design for maintainability.
- GUI responsiveness < 300 ms for log updates.
- Code readability and maintainability with logging (SLF4J/Logback).

## Epics & Patterns
- Epic A — Daily Tracking & Meals (Composite)
- Epic B — Entry Log & Browsing (Iterator)
- Epic C — Goals & Feedback (Observer)
- Epic D — Undo/Redo & Safety (Command)
- Epic E — Reports & Export (Template Method + Hook)
- Epic F — Catalog & Creation (Factory)
- Epic G — Preferences & Startup (Singleton)
- Epic H — Search & Quality of Life

## Build & Run
- Java 17+
- Maven:
  - Build: `mvn clean package`
  - Run (JavaFX plugin): `mvn javafx:run`