package edu.csu.caloriecounter.storage;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Low-level Excel read/write utility using Apache POI.
 *
 * Responsibilities:
 * - Open or create workbook at a given path
 * - Provide helpers to read/write sheets and rows
 *
 * Team: Robert Daniel, Mekaila Cummings, Erik Jasso
 */
public class ExcelStorageProvider {

    public Workbook openOrCreate(Path path) {
        try {
            if (Files.exists(path)) {
                return WorkbookFactory.create(Files.newInputStream(path));
            } else {
                XSSFWorkbook wb = new XSSFWorkbook();
                // TODO: initialize schema sheets
                return wb;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to open/create workbook: " + path, e);
        }
    }

    public void save(Workbook wb, Path path) {
        try (var out = Files.newOutputStream(path)) {
            wb.write(out);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save workbook: " + path, e);
        }
    }
}