package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class OrderOverviewPageController {

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/orderOverview/{hash}",method = RequestMethod.GET)
    public String getOrder(@PathVariable String hash, Model model) {
        model.addAttribute("order", orderService.getOrderByHash(hash));
        return "orderOverview";
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND,
            reason="Order with such hash code wasn't founded")
    @ExceptionHandler(OrderNotFoundException.class)
    public void orderNotFound() {

    }
}
