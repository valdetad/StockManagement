package com.example.StockManagement.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateProductResponse", namespace = "https://example.com/stock")
public class CreateProductResponse {
    private ProductDto product;
}
