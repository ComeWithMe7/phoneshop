package com.es.core.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component("cart")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS )
public class Cart {

    private List<CartItem> cartItems;

    private BigDecimal total;

    private Long productsNumber;

    public Cart() {
        cartItems = new ArrayList<>();
        total = BigDecimal.ZERO;
    }

    public Cart(List<CartItem> cartItems, BigDecimal total) {
        this.cartItems = cartItems;
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getProductsNumber() {
        return productsNumber;
    }

    public void setProductsNumber(Long productsNumber) {
        this.productsNumber = productsNumber;
    }
}
