package com.es.phoneshop.web.controller.pages.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import com.es.phoneshop.web.controller.pages.model.UpdateCartData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UpdateCartService {

    @Resource
    private CartService cartService;

    public UpdateCartData setCartView() {
        Cart cart = cartService.getCart();
        List<CartItemUpdate> cartItemUpdateList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemUpdateList.add(new CartItemUpdate(cartItem.getQuantity(), cartItem.getPhone()));
        }
        return new UpdateCartData(cartItemUpdateList, cart.getTotal());
    }

    public void setIncorrectCartView(UpdateCartData cartView) {
        Cart cart = cartService.getCart();
        List<CartItemUpdate> cartItemUpdateList = cartView.getCartItems();
        List<CartItemUpdate> cartItemUpdates = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Optional<CartItemUpdate> optionalCartItemUpdate = cartItemUpdateList.stream()
                    .filter(x -> x.getPhone().getId().equals(cartItem.getPhone().getId()))
                    .findAny();
            cartItemUpdates.add(new CartItemUpdate(optionalCartItemUpdate.get().getQuantity(), cartItem.getPhone()));
        }
        cartView.setCartItems(cartItemUpdates);
        cartView.setTotal(cart.getTotal());
    }
}
