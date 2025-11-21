package edu.csu.caloriecounter.service;

import edu.csu.caloriecounter.domain.FoodItem;
import edu.csu.caloriecounter.domain.MealType;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Loads a catalog of foods from an Excel workbook and exposes them for UI pre-fill options.
 *
 * <p>The workbook is expected at {@value #CATALOG_RESOURCE} on the classpath with a header row
 * containing the following columns: {@code Description}, {@code Calories}, {@code Protein},
 * {@code Carbs}, {@code Fat}, and {@code MealType}. Each subsequent row is converted into a
 * {@link FoodItem} instance.</p>
 */
@Service
public class FoodCatalogService {
    private static final Logger log = LoggerFactory.getLogger(FoodCatalogService.class);
    private static final String CATALOG_RESOURCE = "data/food-catalog.xlsx";

    private final List<FoodItem> catalog = new ArrayList<>();

    /**
     * Load the catalog from the Excel workbook once at application startup.
     */
    @PostConstruct
    public void loadCatalog() {
        ClassPathResource resource = new ClassPathResource(CATALOG_RESOURCE);
        if (!resource.exists()) {
            log.warn("Food catalog resource '{}' was not found; preset options will be empty", CATALOG_RESOURCE);
            return;
        }

        try (InputStream in = resource.getInputStream(); Workbook workbook = WorkbookFactory.create(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                log.warn("Catalog workbook '{}' contained no sheets", CATALOG_RESOURCE);
                return;
            }

            Row header = sheet.getRow(0);
            Map<String, Integer> columns = resolveColumns(header);
            if (columns.isEmpty()) {
                log.warn("Catalog workbook '{}' does not contain the expected headers", CATALOG_RESOURCE);
                return;
            }

            DataFormatter formatter = new DataFormatter();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                String description = formatter.formatCellValue(row.getCell(columns.get("description"))).trim();
                if (description.isEmpty()) {
                    continue;
                }

                int calories = parseInt(row.getCell(columns.get("calories")), formatter);
                int protein = parseInt(row.getCell(columns.get("protein")), formatter);
                int carbs = parseInt(row.getCell(columns.get("carbs")), formatter);
                int fat = parseInt(row.getCell(columns.get("fat")), formatter);

                MealType mealType = parseMealType(row.getCell(columns.get("mealtype")), formatter);
                if (mealType == null) {
                    log.warn("Skipping row {} due to unknown meal type", r + 1);
                    continue;
                }

                catalog.add(new FoodItem(description, calories, protein, carbs, fat, mealType));
            }

            log.info("Loaded {} preset foods from {}", catalog.size(), CATALOG_RESOURCE);
        } catch (IOException e) {
            log.error("Failed to read food catalog {}", CATALOG_RESOURCE, e);
        }
    }

    /**
     * @return unmodifiable view of loaded foods.
     */
    public List<FoodItem> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    private Map<String, Integer> resolveColumns(Row header) {
        Map<String, Integer> columns = new HashMap<>();
        if (header == null) {
            return columns;
        }

        for (Cell cell : header) {
            String name = cell.getStringCellValue();
            if (name == null) {
                continue;
            }

            switch (name.trim().toLowerCase(Locale.ROOT)) {
                case "description":
                    columns.put("description", cell.getColumnIndex());
                    break;
                case "calories":
                    columns.put("calories", cell.getColumnIndex());
                    break;
                case "protein":
                    columns.put("protein", cell.getColumnIndex());
                    break;
                case "carbs":
                    columns.put("carbs", cell.getColumnIndex());
                    break;
                case "fat":
                    columns.put("fat", cell.getColumnIndex());
                    break;
                case "mealtype":
                    columns.put("mealtype", cell.getColumnIndex());
                    break;
                default:
                    break;
            }
        }
        return columns;
    }

    private int parseInt(Cell cell, DataFormatter formatter) {
        if (cell == null) {
            return 0;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC:
                return (int) Math.round(cell.getNumericCellValue());
            case STRING:
                String value = cell.getStringCellValue();
                try {
                    return Integer.parseInt(value.trim());
                } catch (NumberFormatException ex) {
                    log.warn("Could not parse numeric value '{}' in catalog; defaulting to 0", value);
                    return 0;
                }
            default:
                String formatted = formatter.formatCellValue(cell);
                try {
                    return Integer.parseInt(formatted.trim());
                } catch (NumberFormatException ex) {
                    return 0;
                }
        }
    }

    private MealType parseMealType(Cell cell, DataFormatter formatter) {
        if (cell == null) {
            return null;
        }
        String name = formatter.formatCellValue(cell).trim();
        if (name.isEmpty()) {
            return null;
        }
        try {
            return MealType.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
