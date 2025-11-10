package edu.csu.caloriecounter.report;

import edu.csu.caloriecounter.model.DailyLog;

/**
 * Template Method for exporting a daily report.
 *
 * Hook: includeNotes() determines whether to render optional notes.
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public abstract class AbstractDailyReportExporter {

    public final void export(DailyLog log, ReportContext context) {
        start(context);
        writeHeader(log, context);
        writeRows(log, context);
        if (includeNotes(context)) {
            writeNotes(log, context);
        }
        finish(context);
    }

    protected abstract void start(ReportContext context);
    protected abstract void writeHeader(DailyLog log, ReportContext context);
    protected abstract void writeRows(DailyLog log, ReportContext context);
    protected void writeNotes(DailyLog log, ReportContext context) {}
    protected boolean includeNotes(ReportContext context) { return context.includeNotes(); }
    protected abstract void finish(ReportContext context);
}