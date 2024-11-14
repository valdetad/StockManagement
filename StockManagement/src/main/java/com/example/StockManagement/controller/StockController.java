package com.example.StockManagement.controller;

import com.example.StockManagement.service.MarketService;
import com.example.StockManagement.service.StockService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("stock")
public class StockController {

    private final StockService stockService;
    private final MarketService marketService;

    public StockController(StockService stockService, MarketService marketService) {
        this.stockService = stockService;
        this.marketService = marketService;
    }

    @GetMapping("/{marketId}/export")
    public ResponseEntity<InputStreamResource> exportStock(@PathVariable Long marketId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String filename = "stock-data-for-market-" + marketId + "_" + LocalDateTime.now().format(formatter) + ".xlsx";
        return generateExportResponse(stockService.exportStockToExcel(marketId), filename);
    }

    @GetMapping("/export-to-excel")
    public ResponseEntity<InputStreamResource> exportAllStock() {
        return generateExportResponse(stockService.exportAllStockToExcel(), "all-stock-data.xlsx");
    }

    private ResponseEntity<InputStreamResource> generateExportResponse(ByteArrayInputStream bais, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bais));
    }
}