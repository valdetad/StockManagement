package com.example.StockManagement.controller;

import com.example.StockManagement.data.model.Product;
import com.example.StockManagement.service.ProductService;
import com.example.StockManagement.util.XMLToJSONConverter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductsPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "edit-product";
                })
                .orElse("redirect:/products");
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/upload")
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

    @PostMapping("/convert-xml-to-json")
    public ResponseEntity<String> convertXmlToJson(@RequestBody String xml) {
        try {
            String json = XMLToJSONConverter.convertXMLToJSON(xml);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to convert XML to JSON: " + e.getMessage());
        }
    }
}