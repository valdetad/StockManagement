package com.example.StockManagement.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GetProductRequest", namespace = "https://example.com/stock")
public class GetProductRequest {
    private Long id;
}
