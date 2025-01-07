package com.example.StockManagement.controller;

import com.example.StockManagement.data.model.Stock;
import com.example.StockManagement.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        try {
            Stock savedStock = stockService.saveStock(stock);
            return ResponseEntity.ok(savedStock);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{marketId}/export")
    public ResponseEntity<InputStreamResource> exportStock(@PathVariable Long marketId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String filename = "stock-data-for-market-" + marketId + "_" + LocalDateTime.now().format(formatter) + ".xlsx";
        ByteArrayInputStream bais = stockService.exportStockToExcel(marketId);
        return generateExportResponse(bais, filename);
    }

    @GetMapping("/export-to-excel")
    public ResponseEntity<InputStreamResource> exportAllStock() {
        ByteArrayInputStream bais = stockService.exportAllStockToExcel();
        return generateExportResponse(bais, "all-stock-data.xlsx");
    }

    private ResponseEntity<InputStreamResource> generateExportResponse(ByteArrayInputStream bais, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bais));
    }
}
