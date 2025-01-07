package com.example.StockManagement.data.dtos;

import com.example.StockManagement.data.model.PurchaseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseCreateDto {

    @NotBlank(message = "PURCHASE_STATUS_REQUIRED")
    @NotNull(message = "PURCHASE_STATUS_REQUIRED")
    private PurchaseStatus status;

    @NotBlank(message = "PURCHASE_DATE_REQUIRED")
    @NotNull(message = "PURCHASE_DATE_REQUIRED")
    private Date purchaseDate;

    private Set<Long> productIds;
}
