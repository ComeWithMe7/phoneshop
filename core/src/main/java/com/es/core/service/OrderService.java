package com.es.core.service;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order);
    Order getOrderByHash(String hash);
    List<Order> findAll();
    Order getOrderById(Long orderId);
    void updateStatus(Long orderId, OrderStatus status);
}
