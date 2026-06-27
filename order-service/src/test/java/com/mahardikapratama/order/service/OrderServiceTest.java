package com.mahardikapratama.order.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.mahardikapratama.order.client.CatalogClient;
import com.mahardikapratama.order.client.dto.CatalogProductResponse;
import com.mahardikapratama.order.dto.request.CreateOrderItemRequest;
import com.mahardikapratama.order.dto.request.CreateOrderRequest;
import com.mahardikapratama.order.dto.response.OrderResponse;
import com.mahardikapratama.order.entity.Order;
import com.mahardikapratama.order.entity.OrderItem;
import com.mahardikapratama.order.enums.OrderStatus;
import com.mahardikapratama.order.enums.ProductStatus;
import com.mahardikapratama.order.exception.BusinessException;
import com.mahardikapratama.order.exception.ResourceNotFoundException;
import com.mahardikapratama.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CatalogClient catalogClient;

    @InjectMocks
    private OrderService orderService;

    private UUID orderId;
    private UUID productId;
    private Order order;
    private CreateOrderRequest createOrderRequest;
    private CatalogProductResponse catalogProductResponse;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        productId = UUID.randomUUID();

        // Setup Create Request
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerName("John Doe");
        createOrderRequest.setCustomerEmail("john@example.com");

        CreateOrderItemRequest itemReq = new CreateOrderItemRequest();
        itemReq.setProductId(productId);
        itemReq.setQuantity(2);
        
        List<CreateOrderItemRequest> items = new ArrayList<>();
        items.add(itemReq);
        createOrderRequest.setItems(items);

        // Setup Catalog Product Response
        catalogProductResponse = new CatalogProductResponse();
        catalogProductResponse.setId(productId);
        catalogProductResponse.setName("Test Product");
        catalogProductResponse.setPrice(BigDecimal.valueOf(50000));
        catalogProductResponse.setStock(10);
        catalogProductResponse.setStatus(ProductStatus.ACTIVE);

        // Setup Order Entity
        order = new Order();
        order.setId(orderId);
        order.setCustomerName("John Doe");
        order.setCustomerEmail("john@example.com");
        order.setOrderNumber("ORD-123456");
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.valueOf(100000));
        
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setProductName("Test Product");
        orderItem.setPrice(BigDecimal.valueOf(50000));
        orderItem.setQuantity(2);
        orderItem.setSubtotal(BigDecimal.valueOf(100000));
        orderItem.setOrder(order);
        
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setItems(orderItems);
    }

    @Test
    void createOrder_Success() {
        when(catalogClient.getProduct(productId)).thenReturn(catalogProductResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.createOrder(createOrderRequest);

        assertNotNull(response);
        assertEquals("John Doe", response.getCustomerName());
        assertEquals(OrderStatus.PENDING, response.getStatus());
        verify(catalogClient).updateStock(productId, 8); // 10 - 2
    }

    @Test
    void createOrder_InactiveProduct() {
        catalogProductResponse.setStatus(ProductStatus.INACTIVE);
        when(catalogClient.getProduct(productId)).thenReturn(catalogProductResponse);

        assertThrows(BusinessException.class, () -> orderService.createOrder(createOrderRequest));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_InsufficientStock() {
        catalogProductResponse.setStock(1); // Wants 2, stock 1
        when(catalogClient.getProduct(productId)).thenReturn(catalogProductResponse);

        assertThrows(BusinessException.class, () -> orderService.createOrder(createOrderRequest));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void payOrder_Success() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        OrderResponse response = orderService.payOrder(orderId);

        assertNotNull(response);
        assertEquals(OrderStatus.PAID, response.getStatus());
    }

    @Test
    void payOrder_AlreadyPaid() {
        order.setStatus(OrderStatus.PAID);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(BusinessException.class, () -> orderService.payOrder(orderId));
    }

    @Test
    void cancelOrder_Success() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(catalogClient.getProduct(productId)).thenReturn(catalogProductResponse);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        OrderResponse response = orderService.cancelOrder(orderId);

        assertNotNull(response);
        assertEquals(OrderStatus.CANCELLED, response.getStatus());
        verify(catalogClient).updateStock(productId, 12); // Restored: 10 + 2
    }
}
