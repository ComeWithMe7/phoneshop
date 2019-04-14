package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.pages.model.OrderView;
import com.es.phoneshop.web.controller.pages.service.OrderValidator;
import com.es.phoneshop.web.controller.pages.service.OrderViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Resource
    private OrderViewService orderViewService;

    @Resource
    private CartService cartService;

    @Resource
    private OrderValidator orderValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
        model.addAttribute("orderView", OrderView.setOrderView(cartService.getCart()));
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(Model model, @Valid OrderView orderView, BindingResult bindingResult) {
        Cart cart = cartService.getCart();
        orderValidator.validate(orderView, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("orderView", orderView);
            return "order";
        } else {
            String hash = orderViewService.createOrder(orderView);
            return "redirect:/orderOverview/" + hash;
        }
    }
}
