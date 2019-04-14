package com.es.phoneshop.web.controller.pages.model;

import com.es.core.cart.Cart;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class UpdateCartData {

    @Valid
    private List<CartItemUpdate> cartItems;

    public UpdateCartData() {
        cartItems = new ArrayList<>();
    }

    public UpdateCartData(List<CartItemUpdate> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItemUpdate> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemUpdate> cartItems) {
        this.cartItems = cartItems;
    }

    public static UpdateCartData setCartView(Cart cart) {
        List<CartItemUpdate> cartItemUpdateList = new ArrayList<>();
        cart.getCartItems().forEach(x -> cartItemUpdateList.add(new CartItemUpdate(x.getPhone().getId(), x.getQuantity())));
        return new UpdateCartData(cartItemUpdateList);
    }

}
