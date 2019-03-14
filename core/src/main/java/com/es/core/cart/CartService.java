package com.es.core.cart;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity, Cart cart);

    void update(Map<Long, Long> items);

    void remove(Long phoneId);
}
