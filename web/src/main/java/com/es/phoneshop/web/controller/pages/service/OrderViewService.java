package com.es.phoneshop.web.controller.pages.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.es.phoneshop.web.controller.pages.model.OrderView;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderViewService {

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    public String createOrder(OrderView orderView) {
        Order order = new Order();
        Cart cart = cartService.getCart();
        order.setFirstName(orderView.getFirstName());
        order.setLastName(orderView.getLastName());
        order.setDeliveryAddress(orderView.getAddress());
        order.setContactPhoneNo(orderView.getPhoneNumber());
        order.setInformation(orderView.getInformation());
        order.setHash(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.NEW);
        //order.setSubtotal(cart.getTotal());
        //order.setDeliveryPrice(orderView.getDelivery());
        //order.setTotalPrice(cart.getTotal().add(deliveryPrice));

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(cartItem.getPhone());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        orderService.placeOrder(order);
        return order.getHash();
    }
}
