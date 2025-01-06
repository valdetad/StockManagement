package com.example.StockManagement.controller;

import com.example.StockManagement.data.model.Product;
import com.example.StockManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/import")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            productService.importProducts(file);
            return ResponseEntity.ok("Upload Successful");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to import file: " + e.getMessage());
        }
    }

    @GetMapping("/export-to-excel")
    public ResponseEntity<InputStreamResource> exportAllProductsToExcel() {
        ByteArrayInputStream byteArrayInputStream = productService.exportProductsToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.xlsx");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(byteArrayInputStream));
    }
}
