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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    public void importProducts(MultipartFile file) throws IOException {
        List<Product> products = importExportService.parseProductExcel(file);
        productRepository.saveAll(products);
    }

    public ByteArrayInputStream exportProductsToExcel() {
        List<Product> products = findAll();
        return importExportService.exportProductsToExcel(products);
    }
}
