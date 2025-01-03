package com.example.StockManagement.data.dtos;

import com.example.StockManagement.data.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDto {
    private Category category;
}
