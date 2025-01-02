package com.example.StockManagement.repository;

import com.example.StockManagement.data.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MarketRepository extends CrudRepository<Market, Long> {
    @Query("SELECT AVG(SIZE(m.purchases)) FROM Market m")
    double getAveragePurchases();

    @Query("SELECT m.name FROM Market m JOIN m.purchases p GROUP BY m.id ORDER BY COUNT(p.id) DESC LIMIT 1")
    String getTopMarketByPurchases();
}
