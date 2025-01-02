package com.example.StockManagement.service;

import com.example.StockManagement.data.model.Market;
import com.example.StockManagement.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    public List<Market> findAll() {
        return (List<Market>) marketRepository.findAll();
    }

    public Map<String, Object> getMarketStatistics() {
        long totalMarkets = marketRepository.count();
        double averagePurchases = marketRepository.getAveragePurchases();
        String topMarket = marketRepository.getTopMarketByPurchases();

        // Prepare the statistics map
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMarkets", totalMarkets);
        stats.put("averagePurchases", averagePurchases);
        stats.put("topMarket", topMarket);

        return stats;
    }

    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }
}
