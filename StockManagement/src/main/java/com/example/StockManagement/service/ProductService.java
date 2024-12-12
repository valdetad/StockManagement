package com.example.StockManagement.service;

import com.example.StockManagement.data.model.Product;
import com.example.StockManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImportExportService importExportService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product with ID " + product.getId() + " not found.");
        }
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    public List<Product> importProducts(MultipartFile file) {
        try {
            List<Product> products = importExportService.parseProductExcel(file);
            return productRepository.saveAll(products); // Saves and returns saved products
        } catch (Exception e) {
            throw new RuntimeException("Failed to import products from file: " + e.getMessage(), e);
        }
    }

    public ByteArrayInputStream exportProductsToExcel() {
        List<Product> products = findAll();
        return importExportService.exportProductsToExcel(products);
    }
}
