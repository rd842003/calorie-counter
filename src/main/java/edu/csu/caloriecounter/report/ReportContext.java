package edu.csu.caloriecounter.report;

import java.nio.file.Path;

/**
 * Immutable context for report export.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public record ReportContext(Path targetFile, boolean includeNotes) { }