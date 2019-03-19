package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import com.es.phoneshop.web.controller.pages.model.CartView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@Controller
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute("cartView", setCartView());
        return "cart";
    }

    @RequestMapping(value = "/cart/delete/{phoneId}", method = RequestMethod.POST)
    public String deleteCartItem(@PathVariable long phoneId, Model model) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String updateCart(Model model, @Valid CartView cartView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            setIncorrectCartView(cartView);
            model.addAttribute("cartView", cartView);
            return "cart";
        }
        Set<CartItem> cartItems = new HashSet<>();
        for (CartItemUpdate cartItemUpdate : cartView.getCartItems()) {
            cartItems.add(new CartItem(cartItemUpdate.getPhone(), cartItemUpdate.getQuantity()));
        }
        cartService.update(cartItems);
        return "redirect:/cart";
    }

    public CartView setCartView() {
        Cart cart = cartService.getCart();
        List<CartItemUpdate> cartItemUpdateList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemUpdateList.add(new CartItemUpdate(cartItem.getQuantity(), cartItem.getPhone()));
        }
        return new CartView(cartItemUpdateList, cart.getTotal());
    }

    public void setIncorrectCartView(CartView cartView) {
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
