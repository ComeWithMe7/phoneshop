package com.es.core.order;

import com.es.core.model.order.Order;

public interface OrderService {
    PlaceOrder placeOrder(String firstName, String lastName, String address, String phoneNumber, String information);
    Order getOrder(String hash);
}
