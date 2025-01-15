package com.example.StockManagement.repository.security;

import com.example.StockManagement.data.model.security.model.EHistoryType;
import com.example.StockManagement.data.model.security.model.History;
import com.example.StockManagement.data.model.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);
    List<History> findAllByHistoryType(EHistoryType historyType);
    List<History> findAllByHistoryTypeIn(List<EHistoryType> historyTypes);
    List<History> findAllByHistoryTypeInAndEndDateIsNull(List<EHistoryType> historyTypes);
    boolean existsByUserAndTitleAndStartDateAndEndDate(User user, String title, LocalDate startDate, LocalDate endDate);

}