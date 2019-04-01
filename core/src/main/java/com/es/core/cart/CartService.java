package com.es.core.cart;

import java.util.Set;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void update(Set<CartItem> cartItems);

    void update(Long phoneId, Long quantity);

    void remove(Long phoneId);

    void cleanCart();

    Long countProducts();
}
