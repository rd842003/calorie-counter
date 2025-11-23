package edu.csu.caloriecounter.service;

import edu.csu.caloriecounter.domain.FoodItem;
import edu.csu.caloriecounter.domain.MealType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Loads a catalog of foods from an Excel workbook and exposes them for UI pre-fill options.
 *
 * <p>The workbook is searched for in {@value #DOCS_RESOURCE} first (to make the spreadsheet easy to
 * find and edit). If it is not present there, the service falls back to {@value #CLASSPATH_RESOURCE}
 * on the classpath. The sheet must contain a header row with the columns {@code Description},
 * {@code Calories}, {@code Protein}, {@code Carbs}, {@code Fat}, and {@code MealType}. Each
 * subsequent row is converted into a {@link FoodItem} instance.</p>
 */
@Service
public class FoodCatalogService {
    private static final Logger log = LoggerFactory.getLogger(FoodCatalogService.class);
    private static final String DOCS_RESOURCE = "docs/food-catalog.xlsx";
    private static final String CLASSPATH_RESOURCE = "data/food-catalog.xlsx";
    private static final String DEFAULT_SHEET_NAME = "Foods";

    private final List<FoodItem> catalog = new ArrayList<>();

    /**
     * Load the catalog from the Excel workbook once at application startup.
     */
    @PostConstruct
    public void loadCatalog() {
        Resource resource = resolveCatalogResource();
        if (resource == null) {
            log.warn("No food catalog found in '{}' or classpath resource '{}'", DOCS_RESOURCE, CLASSPATH_RESOURCE);
            return;
        }

        try (InputStream in = resource.getInputStream(); Workbook workbook = WorkbookFactory.create(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                log.warn("Catalog workbook '{}' contained no sheets", resource.getDescription());
                return;
            }

            Row header = sheet.getRow(0);
            Map<String, Integer> columns = resolveColumns(header);
            if (columns.isEmpty()) {
                log.warn("Catalog workbook '{}' does not contain the expected headers", resource.getDescription());
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

            log.info("Loaded {} preset foods from {}", catalog.size(), resource.getDescription());
        } catch (IOException e) {
            log.error("Failed to read food catalog {}", resource.getDescription(), e);
        }
    }

    /**
     * @return unmodifiable view of loaded foods.
     */
    public List<FoodItem> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    /**
     * Append a new food to the in-memory catalog and persist it to the Excel workbook on disk.
     *
     * <p>The append is skipped when the description already exists (case-insensitive) to avoid
     * duplicating rows in the spreadsheet.</p>
     *
     * @param item the food to append
     */
    public synchronized void addToCatalog(FoodItem item) {
        boolean exists = catalog.stream()
            .anyMatch(existing -> existing.getDescription().equalsIgnoreCase(item.getDescription()));
        if (exists) {
            return;
        }

        Path docsPath = resolveDocsPath();
        try (Workbook workbook = openWorkbook(docsPath)) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : workbook.createSheet(DEFAULT_SHEET_NAME);
            ensureHeader(sheet);

            int rowIndex = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(item.getDescription());
            row.createCell(1).setCellValue(item.getCalories());
            row.createCell(2).setCellValue(item.getProtein());
            row.createCell(3).setCellValue(item.getCarbs());
            row.createCell(4).setCellValue(item.getFat());
            row.createCell(5).setCellValue(item.getMealType().name());

            try (OutputStream out = Files.newOutputStream(docsPath)) {
                workbook.write(out);
                catalog.add(item);
                log.info("Appended '{}' to food catalog {}", item.getDescription(), docsPath.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to append food '{}' to catalog {}", item.getDescription(), docsPath.toAbsolutePath(), e);
        }
    }

    private Workbook openWorkbook(Path docsPath) throws IOException {
        if (Files.exists(docsPath)) {
            try (InputStream in = Files.newInputStream(docsPath)) {
                return WorkbookFactory.create(in);
            }
        }

        if (docsPath.getParent() != null) {
            Files.createDirectories(docsPath.getParent());
        }

        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet(DEFAULT_SHEET_NAME);
        return workbook;
    }

    private void ensureHeader(Sheet sheet) {
        Row header = sheet.getRow(0);
        if (header != null && header.getPhysicalNumberOfCells() > 0) {
            return;
        }

        if (header == null) {
            header = sheet.createRow(0);
        }

        header.createCell(0).setCellValue("Description");
        header.createCell(1).setCellValue("Calories");
        header.createCell(2).setCellValue("Protein");
        header.createCell(3).setCellValue("Carbs");
        header.createCell(4).setCellValue("Fat");
        header.createCell(5).setCellValue("MealType");
    }

    private Resource resolveCatalogResource() {
        Resource docsResource = new FileSystemResource(resolveDocsPath());
        if (docsResource.exists()) {
            return docsResource;
        }

        Resource classpathResource = new ClassPathResource(CLASSPATH_RESOURCE);
        if (classpathResource.exists()) {
            return classpathResource;
        }

        return null;
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

    private Path resolveDocsPath() {
        return Path.of(System.getProperty("user.dir"), DOCS_RESOURCE).normalize();
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
