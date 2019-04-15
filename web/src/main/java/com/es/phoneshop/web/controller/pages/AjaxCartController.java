package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.AjaxCartUpdate;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import com.es.phoneshop.web.controller.pages.service.AjaxUpdateService;
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

    @Resource
    private AjaxUpdateService ajaxUpdateService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public AjaxCartUpdate addPhone(HttpServletResponse response, @Valid @RequestBody CartItemUpdate cartItemUpdate, BindingResult errors) {
        if (errors.hasErrors()) {
            response.setStatus(BAD_REQUEST.value());
            return ajaxUpdateService.setAjaxCartUpdateWithError();
        } else {
            cartService.addPhone(cartItemUpdate.getId(), cartItemUpdate.getQuantity());
            return ajaxUpdateService.setAjaxCartUpdate();
        }
    }

}
