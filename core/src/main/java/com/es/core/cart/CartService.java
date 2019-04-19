package com.es.core.cart;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void update(Map<Long, Long> cartItems);

    void update(Long phoneId, Long quantity);

    void remove(Long phoneId);

    void cleanCart();
}
