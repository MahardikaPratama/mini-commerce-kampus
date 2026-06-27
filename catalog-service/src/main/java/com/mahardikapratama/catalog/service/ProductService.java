package com.mahardikapratama.catalog.service;

import com.mahardikapratama.catalog.dto.request.CreateProductRequest;
import com.mahardikapratama.catalog.dto.request.UpdateStatusRequest;
import com.mahardikapratama.catalog.dto.request.UpdateStockRequest;
import com.mahardikapratama.catalog.dto.response.ProductResponse;
import com.mahardikapratama.catalog.entity.Product;
import com.mahardikapratama.catalog.exception.BusinessException;
import com.mahardikapratama.catalog.exception.ResourceNotFoundException;
import com.mahardikapratama.catalog.mapper.ProductMapper;
import com.mahardikapratama.catalog.repository.ProductRepository;
import com.mahardikapratama.catalog.enums.ProductStatus;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

        private final ProductRepository productRepository;

        public ProductService(ProductRepository productRepository) {
                this.productRepository = productRepository;
        }

        public ProductResponse createProduct(CreateProductRequest request) {

                if (productRepository.existsBySku(request.getSku())) {
                        throw new BusinessException("SKU already exists");
                }

                Product product = ProductMapper.toEntity(request);

                Product savedProduct = productRepository.save(product);

                return ProductMapper.toResponse(savedProduct);
        }

        public List<ProductResponse> getAllProducts() {

                return productRepository.findAll()
                                .stream()
                                .map(ProductMapper::toResponse)
                                .toList();
        }

        public ProductResponse getProductById(UUID id) {

                Product product = productRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Product not found"));

                return ProductMapper.toResponse(product);
        }

        public ProductResponse updateStock(
                        UUID id,
                        UpdateStockRequest request) {

                Product product = productRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Product not found"));

                if (product.getStatus() != ProductStatus.ACTIVE) {
                        throw new BusinessException("Cannot update stock of an INACTIVE product");
                }

                product.setStock(request.getStock());

                Product updatedProduct = productRepository.save(product);

                return ProductMapper.toResponse(updatedProduct);
        }

        public ProductResponse updateStatus(
                        UUID id,
                        UpdateStatusRequest request) {

                Product product = productRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Product not found"));

                product.setStatus(request.getStatus());

                Product updatedProduct = productRepository.save(product);

                return ProductMapper.toResponse(updatedProduct);
        }
}