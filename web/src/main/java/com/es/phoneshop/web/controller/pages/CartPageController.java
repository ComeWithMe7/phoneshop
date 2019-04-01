package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import com.es.phoneshop.web.controller.pages.model.UpdateCartData;
import com.es.phoneshop.web.controller.pages.service.UpdateCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CartPageController {

    @Resource
    private CartService cartService;

    @Resource
    private UpdateCartService updateCartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute("cartView", updateCartService.setCartView());
        return "cart";
    }

    @RequestMapping(value = "/cart/delete/{phoneId}", method = RequestMethod.POST)
    public String deleteCartItem(@PathVariable long phoneId, Model model) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String updateCart(Model model, @Valid UpdateCartData cartView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            updateCartService.setIncorrectCartView(cartView);
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
}
