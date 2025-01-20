package com.example.StockManagement.repository.rest;

import com.example.StockManagement.data.model.rest.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByMarketId(Long marketId);
}
