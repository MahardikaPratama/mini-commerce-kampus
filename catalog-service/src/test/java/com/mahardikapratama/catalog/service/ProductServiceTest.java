package com.mahardikapratama.catalog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.mahardikapratama.catalog.dto.request.CreateProductRequest;
import com.mahardikapratama.catalog.dto.request.UpdateStockRequest;
import com.mahardikapratama.catalog.dto.response.ProductResponse;
import com.mahardikapratama.catalog.entity.Product;
import com.mahardikapratama.catalog.enums.ProductStatus;
import com.mahardikapratama.catalog.exception.BusinessException;
import com.mahardikapratama.catalog.exception.ResourceNotFoundException;
import com.mahardikapratama.catalog.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private CreateProductRequest createRequest;
    private Product product;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        
        createRequest = new CreateProductRequest();
        createRequest.setSku("PROD-1");
        createRequest.setName("Product 1");
        createRequest.setPrice(BigDecimal.valueOf(100));
        createRequest.setStock(10);

        product = new Product();
        product.setId(productId);
        product.setSku("PROD-1");
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(100));
        product.setStock(10);
        product.setStatus(ProductStatus.ACTIVE);
    }

    @Test
    void createProduct_Success() {
        when(productRepository.existsBySku(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(createRequest);

        assertNotNull(response);
        assertEquals(createRequest.getSku(), response.getSku());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_DuplicateSku() {
        when(productRepository.existsBySku(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> productService.createProduct(createRequest));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(productId);

        assertNotNull(response);
        assertEquals(productId, response.getId());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void updateStock_Success() {
        UpdateStockRequest req = new UpdateStockRequest();
        req.setStock(20);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        ProductResponse response = productService.updateStock(productId, req);

        assertNotNull(response);
        assertEquals(20, response.getStock());
    }

    @Test
    void updateStock_InactiveProduct() {
        UpdateStockRequest req = new UpdateStockRequest();
        req.setStock(20);
        
        product.setStatus(ProductStatus.INACTIVE);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> productService.updateStock(productId, req));
    }
}
