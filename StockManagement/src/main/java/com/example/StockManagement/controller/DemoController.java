package com.example.StockManagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> getSecret() {
        return ResponseEntity.ok("This is the secret url for USER role");
    }
}