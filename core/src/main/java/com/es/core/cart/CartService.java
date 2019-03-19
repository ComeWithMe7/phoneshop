package com.es.core.cart;

import java.util.Set;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void update(Set<CartItem> cartItems);

    void remove(Long phoneId);
}
