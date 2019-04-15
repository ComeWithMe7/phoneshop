package com.es.phoneshop.web.controller.pages.model;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UpdateCartData {

    @Valid
    private List<CartItemUpdate> cartItems;

    private BigDecimal total;

    public UpdateCartData() {
        cartItems = new ArrayList<>();
        total = BigDecimal.ZERO;
    }

    public UpdateCartData(List<CartItemUpdate> cartItems, BigDecimal total) {
        this.cartItems = cartItems;
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<CartItemUpdate> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemUpdate> cartItems) {
        this.cartItems = cartItems;
    }

}
