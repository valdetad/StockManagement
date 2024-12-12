package com.example.StockManagement.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateProductRequest", namespace = "https://example.com/stock")
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
