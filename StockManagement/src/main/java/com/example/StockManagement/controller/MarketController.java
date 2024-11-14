package com.example.StockManagement.controller;

import com.example.StockManagement.data.model.Market;
import com.example.StockManagement.service.MarketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping
    public List<Market> getAllMarkets() {
        return marketService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteMarket(@PathVariable Long id) {
        marketService.deleteMarket(id);
    }
}
