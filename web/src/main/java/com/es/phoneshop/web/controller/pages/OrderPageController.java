package com.es.phoneshop.web.controller.pages;

import com.es.core.order.OrderService;
import com.es.core.order.PlaceOrder;
import com.es.phoneshop.web.controller.pages.model.OrderView;
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
    private OrderService orderService;

    @Resource
    private OrderViewService orderViewService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) {
        model.addAttribute("orderView", orderViewService.setOrderView());
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(Model model, @Valid OrderView orderView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            orderViewService.setOrderViewWithErrors(orderView);
            model.addAttribute("orderView", orderView);
            return "order";
        } else {
            PlaceOrder placeOrder = orderService.placeOrder(orderView.getFirstName(), orderView.getLastName(), orderView.getAddress(), orderView.getPhoneNumber(), orderView.getInformation());
            if (placeOrder.isSuccessful()) {
                return "redirect:/orderOverview/" + placeOrder.getHash();
            } else {
                model.addAttribute("orderView", orderViewService.setOrderView());
                model.addAttribute("removedProducts", placeOrder.getRemovedProducts());
                return "order";
            }
        }
    }
}
