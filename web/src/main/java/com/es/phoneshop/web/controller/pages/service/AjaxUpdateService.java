package com.es.phoneshop.web.controller.pages.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.AjaxCartUpdate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AjaxUpdateService {

    @Resource
    private CartService cartService;

    public AjaxCartUpdate setAjaxCartUpdate() {
        AjaxCartUpdate ajaxResponse = new AjaxCartUpdate();
        Cart cart = cartService.getCart();
        ajaxResponse.setTotal(cart.getTotal());
        ajaxResponse.setCount(cartService.countProducts());
        return ajaxResponse;
    }

    public AjaxCartUpdate setAjaxCartUpdateWithError() {
        AjaxCartUpdate ajaxResponse = new AjaxCartUpdate();
        Cart cart = cartService.getCart();
        ajaxResponse.setTotal(cart.getTotal());
        ajaxResponse.setQuantityError("error");
        ajaxResponse.setCount(cartService.countProducts());
        return ajaxResponse;
    }
}
