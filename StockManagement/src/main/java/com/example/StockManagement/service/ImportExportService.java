package com.example.StockManagement.service;

import com.example.StockManagement.data.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImportExportService {

    public List<Product> parseProductExcel(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip the header row
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Product product = new Product();

                // Read Product ID (assuming it’s numeric)
                Cell idCell = currentRow.getCell(0);
                if (idCell != null && idCell.getCellType() == CellType.NUMERIC) {
                    product.setId((long) idCell.getNumericCellValue());
                }

                // Read Product Name
                Cell nameCell = currentRow.getCell(1);
                if (nameCell != null) {
                    if (nameCell.getCellType() == CellType.STRING) {
                        product.setName(nameCell.getStringCellValue());
                    } else if (nameCell.getCellType() == CellType.NUMERIC) {
                        product.setName(String.valueOf((int) nameCell.getNumericCellValue()));
                    }
                }

                // Read Description
                Cell descriptionCell = currentRow.getCell(2);
                if (descriptionCell != null) {
                    if (descriptionCell.getCellType() == CellType.STRING) {
                        product.setDescription(descriptionCell.getStringCellValue());
                    } else if (descriptionCell.getCellType() == CellType.NUMERIC) {
                        product.setDescription(String.valueOf((int) descriptionCell.getNumericCellValue()));
                    }
                }

                // Read Price (set default if null or invalid type)
                Cell priceCell = currentRow.getCell(3);
                if (priceCell != null && priceCell.getCellType() == CellType.NUMERIC) {
                    product.setPrice(priceCell.getNumericCellValue());
                } else {
                    product.setPrice(0.0); // Default price if not present
                }

                // Read Quantity (set default if null or invalid type)
                Cell quantityCell = currentRow.getCell(4);
                if (quantityCell != null && quantityCell.getCellType() == CellType.NUMERIC) {
                    product.setQuantity((int) quantityCell.getNumericCellValue());
                } else {
                    product.setQuantity(0); // Default quantity if not present
                }

                products.add(product);
            }
        }

        return products;
    }

    public ByteArrayInputStream exportProductsToExcel(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Product ID");
            headerRow.createCell(1).setCellValue("Product Name");
            headerRow.createCell(2).setCellValue("Description");
            headerRow.createCell(3).setCellValue("Price");
            headerRow.createCell(4).setCellValue("Quantity");

            // Populate rows with product data
            int rowIndex = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(product.getId() != null ? product.getId() : 0);
                row.createCell(1).setCellValue(product.getName() != null ? product.getName() : "");
                row.createCell(2).setCellValue(product.getDescription() != null ? product.getDescription() : "");

                // Check for null price and quantity, set default values if necessary
                row.createCell(3).setCellValue(product.getPrice() != null ? product.getPrice() : 0.0);
                row.createCell(4).setCellValue(product.getQuantity() != null ? product.getQuantity() : 0);
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return new ByteArrayInputStream(outputStream.toByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to export products to Excel file", e);
        }
    }

}