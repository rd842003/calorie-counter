/**
 * Top-level package for the Calorie Counter application.
 *
 * This package contains the application bootstrap, configuration classes, domain objects,
 * repositories, services, and web controllers used by the Calorie Counter demo application.
 *
 * Patterns Used So Far:
 * - MVC (Spring): Controllers ↔ Service ↔ Views (Thymeleaf)
 * - Repository (DAO): JPA repository for LogEntry
 * - Singleton (via Spring): beans are container-scoped singletons
 * - Strategy (extension point): calculations centralized in LogService for later strategy injection
 */
package edu.csu.caloriecounter;