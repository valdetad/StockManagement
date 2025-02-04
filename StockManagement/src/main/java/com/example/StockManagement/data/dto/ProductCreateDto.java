package com.example.StockManagement.data.dto;

import com.example.StockManagement.data.constants.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateDto {

    @NotBlank(message = "PRODUCT_NAME_REQUIRED")
    @NotNull(message = "PRODUCT_NAME_REQUIRED")
    private String name;

    private Category category;

    @NotBlank(message = "PRODUCT_PRICE_REQUIRED")
    private Double price;

    private String description;

    @NotBlank(message = "PRODUCT_BARCODE_REQUIRED")
    private String barcode;

}
