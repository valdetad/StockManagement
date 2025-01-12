package com.example.StockManagement.data.dtos.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MarketCreateDto {

    @NotBlank(message = "MARKET_NAME_REQUIRED")
    @NotNull(message = "MARKET_NAME_REQUIRED")
    private String name;
}
