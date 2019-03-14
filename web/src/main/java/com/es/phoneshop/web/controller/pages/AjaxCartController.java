package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.AjaxCartUpdate;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public AjaxCartUpdate addPhone(@Valid @RequestBody CartItemUpdate cartItemUpdate, BindingResult errors) {
        AjaxCartUpdate ajaxResponse = new AjaxCartUpdate();
        Cart cart = cartService.getCart();
        if (errors.hasErrors()) {
            ajaxResponse.setQuantityError("error");
        } else {
            cartService.addPhone(cartItemUpdate.getId(), cartItemUpdate.getQuantity(), cart);
        }
        ajaxResponse.setTotal(cart.getTotal());
        return ajaxResponse;
    }
}
