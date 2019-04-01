package com.es.phoneshop.web.controller.pages.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import com.es.phoneshop.web.controller.pages.model.OrderView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OrderViewService {

    @Resource
    private CartService cartService;

    @Resource
    private MessageSource messageSource;

    public OrderView setOrderView() {
        OrderView orderView = new OrderView();
        Cart cart = cartService.getCart();
        List<CartItemUpdate> cartItemUpdateList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemUpdateList.add(new CartItemUpdate(cartItem.getQuantity(), cartItem.getPhone()));
        }
        orderView.setCartItemUpdates(cartItemUpdateList);
        orderView.setSubtotal(cart.getTotal());
        orderView.setDelivery(BigDecimal.valueOf(Double.parseDouble(messageSource.getMessage("delivery.price", null, Locale.getDefault()))));
        orderView.setTotal(orderView.getSubtotal().add(orderView.getDelivery()));
        return orderView;
    }

    public void setOrderViewWithErrors(OrderView orderView) {
        Cart cart = cartService.getCart();
        List<CartItemUpdate> cartItemUpdateList = orderView.getCartItemUpdates();
        List<CartItemUpdate> cartItemUpdates = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Optional<CartItemUpdate> optionalCartItemUpdate = cartItemUpdateList.stream()
                    .filter(x -> x.getPhone().getId().equals(cartItem.getPhone().getId()))
                    .findAny();
            cartItemUpdates.add(new CartItemUpdate(optionalCartItemUpdate.get().getQuantity(), cartItem.getPhone()));
        }
        orderView.setCartItemUpdates(cartItemUpdates);
        orderView.setSubtotal(cart.getTotal());
        orderView.setDelivery(BigDecimal.valueOf(Double.parseDouble(messageSource.getMessage("delivery.price", null, Locale.getDefault()))));
        orderView.setTotal(orderView.getSubtotal().add(orderView.getDelivery()));
    }

}
