package com.mahardikapratama.catalog.mapper;

import com.mahardikapratama.catalog.dto.request.CreateProductRequest;
import com.mahardikapratama.catalog.dto.response.ProductResponse;
import com.mahardikapratama.catalog.entity.Product;

public class ProductMapper {

    public static Product toEntity(CreateProductRequest request) {
        Product product = new Product();

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return product;
    }

    public static ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setSku(product.getSku());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setStatus(product.getStatus());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}