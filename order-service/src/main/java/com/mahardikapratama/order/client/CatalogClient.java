package com.mahardikapratama.order.client;

import com.mahardikapratama.order.client.dto.CatalogProductResponse;
import com.mahardikapratama.order.client.dto.UpdateStockRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class CatalogClient {

    private final RestClient restClient;

    public CatalogClient(
            @Value("${catalog.service.url}")
            String catalogUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(catalogUrl)
                .build();
    }

    public CatalogProductResponse getProduct(
            UUID productId) {

        return restClient.get()
                .uri("/api/products/{id}", productId)
                .retrieve()
                .body(CatalogProductResponse.class);
    }

    public void updateStock(
            UUID productId,
            Integer stock) {

        restClient.patch()
                .uri("/api/products/{id}/stock", productId)
                .body(new UpdateStockRequest(stock))
                .retrieve()
                .toBodilessEntity();
    }
}