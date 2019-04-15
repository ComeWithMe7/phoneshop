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

    public Cart() {
        cartItems = new HashSet<>();
        total = BigDecimal.ZERO;
    }

    public Cart(Set<CartItem> cartItems, BigDecimal total) {
        this.cartItems = cartItems;
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
