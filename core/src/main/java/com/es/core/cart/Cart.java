package com.es.core.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component("cart")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS )
public class Cart {

    private Set<CartItem> cartItems;
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cart() {
        cartItems = new HashSet<>();
        total = BigDecimal.ZERO;
    }

    public void add(CartItem cartItem) {
        cartItems.add(cartItem);
    }
}
