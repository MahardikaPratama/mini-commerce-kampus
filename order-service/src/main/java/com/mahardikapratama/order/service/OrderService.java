package com.mahardikapratama.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
import com.mahardikapratama.order.mapper.OrderMapper;
import com.mahardikapratama.order.repository.OrderRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CatalogClient catalogClient;

    public OrderService(
            OrderRepository orderRepository,
            CatalogClient catalogClient) {

        this.orderRepository = orderRepository;
        this.catalogClient = catalogClient;
    }

    @Transactional
    public OrderResponse createOrder(
            CreateOrderRequest request) {

        Order order = new Order();

        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());

        order.setOrderNumber(
                "ORD-" + System.currentTimeMillis());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderItemRequest itemRequest : request.getItems()) {

            CatalogProductResponse product =
                    catalogClient.getProduct(
                            itemRequest.getProductId());

            if (product.getStatus() != ProductStatus.ACTIVE) {
                throw new BusinessException(
                        "Product is inactive");
            }

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new BusinessException(
                        "Insufficient stock for product: "
                                + product.getName());
            }

            BigDecimal subtotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemRequest.getQuantity()));

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSubtotal(subtotal);

            orderItem.setOrder(order);

            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(subtotal);

            int newStock =
                    product.getStock()
                            - itemRequest.getQuantity();

            catalogClient.updateStock(
                    product.getId(),
                    newStock);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        return OrderMapper.toResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));

        return OrderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponse payOrder(UUID id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(
                    "Only PENDING orders can be paid");
        }

        order.setStatus(OrderStatus.PAID);

        Order updatedOrder =
                orderRepository.save(order);

        return OrderMapper.toResponse(updatedOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(UUID id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(
                    "Only PENDING orders can be cancelled");
        }

        for (OrderItem item : order.getItems()) {

            CatalogProductResponse product =
                    catalogClient.getProduct(
                            item.getProductId());

            int restoredStock =
                    product.getStock()
                            + item.getQuantity();

            catalogClient.updateStock(
                    product.getId(),
                    restoredStock);
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order updatedOrder =
                orderRepository.save(order);

        return OrderMapper.toResponse(updatedOrder);
    }

}