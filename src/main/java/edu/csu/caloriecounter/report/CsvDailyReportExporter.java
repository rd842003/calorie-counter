package edu.csu.caloriecounter.report;

import edu.csu.caloriecounter.model.DailyLog;
import edu.csu.caloriecounter.model.Entry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * CSV exporter for daily report (Template Method concrete impl).
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class CsvDailyReportExporter extends AbstractDailyReportExporter {
    private CSVPrinter printer;

    @Override
    protected void start(ReportContext context) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(context.targetFile());
            printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                "Date","MealType","Food","Calories","Protein","Carbs","Fat","Notes"
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeHeader(DailyLog log, ReportContext context) {
        // Header written by CSVPrinter.withHeader
    }

    @Override
    protected void writeRows(DailyLog log, ReportContext context) {
        try {
            for (Entry e : log.getEntries()) {
                printer.printRecord(
                    e.getDate(),
                    e.getMealType(),
                    e.getFood().getName(),
                    e.getFood().getCalories(),
                    e.getFood().getMacros() != null ? e.getFood().getMacros().protein() : "",
                    e.getFood().getMacros() != null ? e.getFood().getMacros().carbs() : "",
                    e.getFood().getMacros() != null ? e.getFood().getMacros().fat() : "",
                    context.includeNotes() ? e.getNotes() : ""
                );
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void finish(ReportContext context) {
        try {
            if (printer != null) printer.close(true);
        } catch (IOException e) {
            // ignore
        }
    }
}