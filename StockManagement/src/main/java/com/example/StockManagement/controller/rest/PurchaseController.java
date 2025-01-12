package com.example.StockManagement.controller.rest;

import com.example.StockManagement.service.rest.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/export")
    public ResponseEntity<?> exportPurchases(@RequestParam("marketId") Long marketId) {
        ByteArrayInputStream bais = purchaseService.exportPurchasesToPdf(marketId);

        if (bais == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate PDF for marketId: " + marketId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=purchase-for-market-" + marketId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(bais));
    }
}
