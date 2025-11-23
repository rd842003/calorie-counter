package edu.csu.caloriecounter.service;

import edu.csu.caloriecounter.domain.FoodItem;
import edu.csu.caloriecounter.domain.MealType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FoodCatalogServiceTest {

    @Test
    void loadCatalogReadsFromDocsResource(@TempDir Path tempDir) throws IOException {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Path workbookPath = docs.resolve("food-catalog.xlsx");
        writeSampleWorkbook(workbookPath);

        withUserDir(tempDir, () -> {
            FoodCatalogService service = new FoodCatalogService();
            service.loadCatalog();

            List<FoodItem> catalog = service.getCatalog();
            assertThat(catalog).hasSize(2);
            assertThat(catalog.get(0).getDescription()).isEqualTo("Oatmeal");
            assertThat(catalog.get(0).getMealType()).isEqualTo(MealType.BREAKFAST);
            assertThat(catalog.get(1).getCalories()).isEqualTo(400);
        });
    }

    @Test
    void addToCatalogCreatesWorkbookAndSkipsDuplicates(@TempDir Path tempDir) throws IOException {
        Path docs = tempDir.resolve("docs");
        Files.createDirectories(docs);
        Path workbookPath = docs.resolve("food-catalog.xlsx");

        withUserDir(tempDir, () -> {
            FoodCatalogService service = new FoodCatalogService();
            FoodItem soup = new FoodItem("Soup", 250, 12, 28, 8, MealType.LUNCH);

            service.addToCatalog(soup);
            service.addToCatalog(new FoodItem("soup", 300, 15, 30, 10, MealType.DINNER));

            assertThat(service.getCatalog()).hasSize(1);
            assertThat(workbookPath).exists();

            try (InputStream in = Files.newInputStream(workbookPath); Workbook workbook = new XSSFWorkbook(in)) {
                Sheet sheet = workbook.getSheetAt(0);
                assertThat(sheet.getLastRowNum()).isEqualTo(1); // header + one data row

                Row row = sheet.getRow(1);
                assertThat(row.getCell(0).getStringCellValue()).isEqualTo("Soup");
                assertThat(row.getCell(1).getNumericCellValue()).isEqualTo(250);
                assertThat(row.getCell(5).getStringCellValue()).isEqualTo("LUNCH");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void writeSampleWorkbook(Path path) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Foods");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Description");
            header.createCell(1).setCellValue("Calories");
            header.createCell(2).setCellValue("Protein");
            header.createCell(3).setCellValue("Carbs");
            header.createCell(4).setCellValue("Fat");
            header.createCell(5).setCellValue("MealType");

            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("Oatmeal");
            row1.createCell(1).setCellValue(300);
            row1.createCell(2).setCellValue(10);
            row1.createCell(3).setCellValue(55);
            row1.createCell(4).setCellValue(5);
            row1.createCell(5).setCellValue("BREAKFAST");

            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("Burrito");
            row2.createCell(1).setCellValue(400);
            row2.createCell(2).setCellValue(20);
            row2.createCell(3).setCellValue(45);
            row2.createCell(4).setCellValue(12);
            row2.createCell(5).setCellValue("DINNER");

            try (OutputStream out = Files.newOutputStream(path)) {
                workbook.write(out);
            }
        }
    }

    private void withUserDir(Path dir, Runnable runnable) {
        String original = System.getProperty("user.dir");
        System.setProperty("user.dir", dir.toString());
        try {
            runnable.run();
        } finally {
            System.setProperty("user.dir", original);
        }
    }
}
