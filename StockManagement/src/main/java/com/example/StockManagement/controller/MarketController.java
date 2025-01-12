package com.example.StockManagement.controller;

import com.example.StockManagement.data.model.rest.Market;
import com.example.StockManagement.service.rest.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping
    public List<Market> getAllMarkets() {
        return marketService.findAll();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getMarketStatistics() {
        Map<String, Object> statistics = marketService.getMarketStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarket(@PathVariable Long id) {
        marketService.deleteMarket(id);
        return ResponseEntity.noContent().build();
    }
}
