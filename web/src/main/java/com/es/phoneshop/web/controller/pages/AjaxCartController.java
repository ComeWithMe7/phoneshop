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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public AjaxCartUpdate addPhone(HttpServletResponse response, @Valid @RequestBody CartItemUpdate cartItemUpdate, BindingResult errors) {
        Cart cart = cartService.getCart();
        if (errors.hasErrors()) {
            response.setStatus(BAD_REQUEST.value());
            return AjaxCartUpdate.createAjaxCartUpdateWithError(cart, errors.getFieldError("quantity").getDefaultMessage());
        } else {
            cartService.addPhone(cartItemUpdate.getId(), cartItemUpdate.getQuantity());
            return AjaxCartUpdate.createAjaxCartUpdate(cart);
        }
    }

}
