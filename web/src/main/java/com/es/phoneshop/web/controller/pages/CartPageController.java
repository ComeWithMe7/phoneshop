package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.phoneshop.web.model.CartItemUpdate;
import com.es.phoneshop.web.model.UpdateCartData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("updateCartData", UpdateCartData.setCartView(cartService.getCart()));
        return "cart";
    }

    @DeleteMapping(value = "/delete/{phoneId}")
    public String deleteCartItem(@PathVariable long phoneId, Model model) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

    @PutMapping
    public String updateCart(Model model, @Valid UpdateCartData updateCartData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateCartData", updateCartData);
            return "cart";
        }
        Map<Long, Long> cartItems = new HashMap<>();
        for (CartItemUpdate cartItemUpdate : updateCartData.getCartItems()) {
            cartItems.put(cartItemUpdate.getId(), cartItemUpdate.getQuantity());
        }
        cartService.update(cartItems);
        return "redirect:/cart";
    }
}
