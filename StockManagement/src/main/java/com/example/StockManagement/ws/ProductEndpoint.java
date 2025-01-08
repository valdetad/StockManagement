package com.example.StockManagement.ws;

import com.example.StockManagement.dto.soap.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.Map;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "https://example.com/stock";

    // Mock database for demonstration
    private final Map<Long, ProductDto> productDatabase = new HashMap<>();

    public ProductEndpoint() {
        // Add mock data to the database
        ProductDto product = new ProductDto(1L, "Existing Product", "Description of existing product", 1000.0, 10);
        productDatabase.put(product.getId(), product);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        ProductDto product = productDatabase.get(request.getId());

        if (product != null) {
            response.setProduct(product);
        } else {
            throw new RuntimeException("Product with ID " + request.getId() + " not found.");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateProductRequest")
    @ResponsePayload
    public CreateProductResponse createProduct(@RequestPayload CreateProductRequest request) {
        CreateProductResponse response = new CreateProductResponse();

        // Generate a new product ID
        long newId = productDatabase.size() + 1;

        // Create and save the new product
        ProductDto newProduct = new ProductDto(newId, request.getName(), request.getDescription(), request.getPrice(), request.getQuantity());
        productDatabase.put(newId, newProduct);

        response.setProduct(newProduct);
        return response;
    }
}
