package com.example.StockManagement.data.model.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;
    private String historyType;
}
