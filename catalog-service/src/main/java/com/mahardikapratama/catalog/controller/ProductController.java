package com.mahardikapratama.catalog.controller;

import com.mahardikapratama.catalog.dto.request.CreateProductRequest;
import com.mahardikapratama.catalog.dto.request.UpdateStatusRequest;
import com.mahardikapratama.catalog.dto.request.UpdateStockRequest;
import com.mahardikapratama.catalog.dto.response.ProductResponse;
import com.mahardikapratama.catalog.service.ProductService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

        private final ProductService productService;

        public ProductController(ProductService productService) {
                this.productService = productService;
        }

        @PostMapping
        public ResponseEntity<ProductResponse> createProduct(
                        @Valid @RequestBody CreateProductRequest request) {

                ProductResponse response = productService.createProduct(request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @GetMapping
        public ResponseEntity<List<ProductResponse>> getAllProducts() {

                return ResponseEntity.ok(
                                productService.getAllProducts());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> getProductById(
                        @PathVariable UUID id) {

                return ResponseEntity.ok(
                                productService.getProductById(id));
        }

        @PatchMapping("/{id}/stock")
        public ResponseEntity<ProductResponse> updateStock(
                        @PathVariable UUID id,
                        @Valid @RequestBody UpdateStockRequest request) {

                return ResponseEntity.ok(
                                productService.updateStock(id, request));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<ProductResponse> updateStatus(
                        @PathVariable UUID id,
                        @Valid @RequestBody UpdateStatusRequest request) {

                return ResponseEntity.ok(
                                productService.updateStatus(id, request));
        }
}