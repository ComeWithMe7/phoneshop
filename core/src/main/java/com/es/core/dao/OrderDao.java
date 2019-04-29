package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    void save(Order order);

    Optional<Order> get(String hash);

    List<Order> findAll();

    Optional<Order> getById(Long orderId);

    void updateStatus(Long orderId, OrderStatus status);
}
