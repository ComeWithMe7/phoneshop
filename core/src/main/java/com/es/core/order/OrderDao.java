package com.es.core.order;

import com.es.core.model.order.Order;

import java.util.Optional;

public interface OrderDao {

    public void save(Order order);

    public Optional<Order> get(String hash);
}
