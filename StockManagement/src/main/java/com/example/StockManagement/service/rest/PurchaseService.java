package com.example.StockManagement.service.rest;

import com.example.StockManagement.data.model.rest.Product;
import com.example.StockManagement.data.model.rest.Purchase;
import com.example.StockManagement.data.model.rest.Market;
import com.example.StockManagement.data.model.rest.Stock;
import com.example.StockManagement.repository.rest.MarketRepository;
import com.example.StockManagement.repository.rest.PurchaseRepository;
import com.example.StockManagement.repository.rest.StockRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final MarketRepository marketRepository;
    private final PurchaseRepository purchaseRepository;
    private final StockRepository stockRepository;

    @Autowired
    public PurchaseService(MarketRepository marketRepository, PurchaseRepository purchaseRepository, StockRepository stockRepository) {
        this.marketRepository = marketRepository;
        this.purchaseRepository = purchaseRepository;
        this.stockRepository = stockRepository;
    }

    public ByteArrayInputStream exportPurchasesToPdf(Long marketId) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Optional<Market> marketOpt = marketRepository.findById(marketId);
            if (marketOpt.isEmpty()) {
                document.add(new Paragraph("Market with ID " + marketId + " NOT FOUND."));
                document.close();
                return new ByteArrayInputStream(out.toByteArray());
            }

            Market market = marketOpt.get();
            List<Purchase> purchases = purchaseRepository.findByMarketId(marketId);
            market.setPurchases(purchases);

            String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            addMarketInfo(document, market.getName(), currentDate);
            addPurchaseTable(document, market);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addMarketInfo(Document document, String marketName, String currentDate) throws DocumentException {
        document.add(new Paragraph("Market: " + marketName));
        document.add(new Paragraph("Purchase Date: " + currentDate));
        document.add(new Paragraph(" "));
    }

    private void addPurchaseTable(Document document, Market market) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        addTableHeaders(table);

        double overallTotal = addMarketPurchases(table, market);

        if (overallTotal > 0) {
            addOverallTotalRow(table, overallTotal);
        } else {
            table.addCell("No purchases found for this market.");
            table.addCell("");
            table.addCell("");
            table.addCell("");
        }

        document.add(table);
    }

    private void addTableHeaders(PdfPTable table) {
        table.addCell("Product Name");
        table.addCell("Quantity");
        table.addCell("Price");
        table.addCell("Total");
    }

    private double addMarketPurchases(PdfPTable table, Market market) {
        double overallTotal = 0.0;

        if (market.getPurchases() == null || market.getPurchases().isEmpty()) {
            return overallTotal;
        }

        for (Purchase purchase : market.getPurchases()) {
            if (purchase.getProducts() == null || purchase.getProducts().isEmpty()) {
                continue;
            }

            for (Product product : purchase.getProducts()) {
                Optional<Stock> stockOpt = stockRepository.findByProductIdAndMarketId(product.getId(), market.getId());

                if (stockOpt.isEmpty()) {
                    continue;
                }

                int quantity = stockOpt.get().getQuantity();
                double price = product.getPrice();
                double total = quantity * price;

                if (quantity > 0) {
                    table.addCell(product.getName());
                    table.addCell(String.valueOf(quantity));
                    table.addCell(String.format("%.2f", price));
                    table.addCell(String.format("%.2f", total));
                    overallTotal += total;
                }
            }
        }

        return overallTotal;
    }

    private void addOverallTotalRow(PdfPTable table, double overallTotal) {
        table.addCell("Overall Total");
        table.addCell("");
        table.addCell("");
        table.addCell(String.format("%.2f", overallTotal));
    }
}
