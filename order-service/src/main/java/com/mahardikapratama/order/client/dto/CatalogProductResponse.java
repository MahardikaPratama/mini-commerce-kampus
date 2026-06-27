package com.mahardikapratama.order.client.dto;

import com.mahardikapratama.order.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CatalogProductResponse {

    private UUID id;

    private String sku;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private ProductStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}