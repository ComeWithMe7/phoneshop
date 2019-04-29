package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders";
    }

    @GetMapping(value = "/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "adminOrderOverview";
    }

    @PutMapping(value = "/{orderId}")
    public String update(@PathVariable Long orderId, Model model, @RequestParam(name = "status") OrderStatus status) {
        orderService.updateStatus(orderId, status);
        return "redirect:/admin/orders/" + orderId;
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND,
            reason="Order with such Id wasn't founded")
    @ExceptionHandler(OrderNotFoundException.class)
    public void orderNotFound() {    }
}
