package com.es.phoneshop.web.controller.pages;

import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class OrderOverviewPageController {

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/orderOverview/{hash}",method = RequestMethod.GET)
    public String getOrder(@PathVariable String hash, Model model) {
        model.addAttribute("order", orderService.getOrder(hash));
        return "orderOverview";
    }
}
