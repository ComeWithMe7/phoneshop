package com.es.phoneshop.web.controller.pages.service;

import com.es.core.cart.CartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;

@ControllerAdvice
public class CartControllerAdvice {

    @Resource
    private CartService cartService;

    @ModelAttribute
    public void putCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
    }
}
