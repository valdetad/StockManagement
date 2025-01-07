package com.example.StockManagement.data.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockCreateDto {

    @NotBlank(message = "STOCK_QUANTITY_REQUIRED")
    @NotNull(message = "STOCK_QUANTITY_REQUIRED")
    private Integer quantity;

    private Long marketId;
    private Long productId;

}
