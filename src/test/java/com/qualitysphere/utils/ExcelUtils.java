
package com.qualitysphere.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class ExcelUtils {

    public static Object[][] readExcelData(
            String fileName, String sheetName) {

        try (
                InputStream input = ExcelUtils.class
                        .getClassLoader()
                        .getResourceAsStream("testdata/" + fileName);

                Workbook workbook = new XSSFWorkbook(input)
        ) {

            if (input == null) {
                throw new RuntimeException(
                        "Excel file not found: " + fileName
                );
            }

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException(
                        "Sheet not found: " + sheetName
                );
            }

            int totalRows = sheet.getLastRowNum();

            Object[][] data = new Object[totalRows][3];

            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= totalRows; i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    throw new RuntimeException(
                            "Empty row found at row: " + (i + 1)
                    );
                }

                String username = formatter.formatCellValue(
                        row.getCell(0)
                );

                String password = formatter.formatCellValue(
                        row.getCell(1)
                );

                String expectedValue = formatter.formatCellValue(
                        row.getCell(2)
                );

                boolean expectedSuccess =
                        Boolean.parseBoolean(expectedValue);

                data[i - 1][0] = username;
                data[i - 1][1] = password;
                data[i - 1][2] = expectedSuccess;
            }

            return data;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to read Excel test data!", e
            );
        }
    }
}
