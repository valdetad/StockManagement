package com.example.StockManagement.data.dtos.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", namespace = "https://example.com/stock", propOrder = {"id", "name", "description", "price", "quantity"})
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
