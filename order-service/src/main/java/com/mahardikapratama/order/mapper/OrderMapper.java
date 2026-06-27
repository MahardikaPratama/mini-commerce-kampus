package com.mahardikapratama.order.mapper;

import com.mahardikapratama.order.dto.response.OrderItemResponse;
import com.mahardikapratama.order.dto.response.OrderResponse;
import com.mahardikapratama.order.entity.Order;
import com.mahardikapratama.order.entity.OrderItem;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponse toResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setCustomerName(order.getCustomerName());
        response.setCustomerEmail(order.getCustomerEmail());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        response.setItems(
                order.getItems()
                        .stream()
                        .map(OrderMapper::toItemResponse)
                        .toList()
        );

        return response;
    }

    private static OrderItemResponse toItemResponse(
            OrderItem item) {

        OrderItemResponse response =
                new OrderItemResponse();

        response.setProductId(item.getProductId());
        response.setProductName(item.getProductName());
        response.setPrice(item.getPrice());
        response.setQuantity(item.getQuantity());
        response.setSubtotal(item.getSubtotal());

        return response;
    }
}