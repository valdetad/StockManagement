package com.example.StockManagement.services;

import com.example.StockManagement.data.model.Product;
import com.example.StockManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImportExportService importExportService;

    // Retrieve all products
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Retrieve a product by ID
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // Search products by name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Save a product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " not found.");
        }
        return productRepository.save(product);
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    // Import products from an uploaded file
    public List<Product> importProducts(MultipartFile file) throws IOException {
        List<Product> products = importExportService.parseProductExcel(file);
        return productRepository.saveAll(products);
    }

    // Export all products to an Excel file
    public ByteArrayInputStream exportProductsToExcel() {
        List<Product> products = findAll();
        return importExportService.exportProductsToExcel(products);
    }
}
