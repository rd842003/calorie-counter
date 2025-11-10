# Architecture Overview

This document describes the package layout and design patterns implemented.

## Packages
- `edu.csu.caloriecounter.app` — JavaFX bootstrap
- `edu.csu.caloriecounter.settings` — Singleton SettingsManager
- `edu.csu.caloriecounter.model` — Domain entities (Food, Meal, Entry, DailyLog, MacroProfile, MealType)
- `edu.csu.caloriecounter.command` — Command pattern (commands + CommandManager)
- `edu.csu.caloriecounter.observer` — LogObserver interface and dashboard observers
- `edu.csu.caloriecounter.iterator` — Entry iteration and filtering (future)
- `edu.csu.caloriecounter.catalog` — FoodFactory and CatalogService from Excel (future)
- `edu.csu.caloriecounter.storage` — Apache POI Excel IO
- `edu.csu.caloriecounter.report` — Template Method export (CSV/JSON)
- `edu.csu.caloriecounter.search` — FoodSearchService and optional indexing (future)
- `edu.csu.caloriecounter.ui` — Controllers and FXML (future)

## Pattern Mapping
- Composite: Meal aggregates Foods/Meals.
- Iterator: Consistent traversal across Entries with filters.
- Observer: DailyLog notifies UI observers on changes.
- Command: Undo/Redo for add/remove operations.
- Template Method + Hook: CSV/JSON exporters, includeNotes toggle.
- Factory: FoodFactory creates Foods from Excel rows.
- Singleton: SettingsManager centralizes preferences.

## Persistence
Excel sheets: Users, Foods, Entries, Goals via Apache POI.
Storage provider abstracts file IO and schema management.

## Logging
SLF4J API with Logback backend, rolling file appender.