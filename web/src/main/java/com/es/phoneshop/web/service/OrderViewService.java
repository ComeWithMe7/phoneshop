package com.es.phoneshop.web.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.service.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.OrderService;
import com.es.phoneshop.web.model.CartItemUpdate;
import com.es.phoneshop.web.model.OrderView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderViewService {

    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

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
        order.setSubtotal(cart.getTotal());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(cart.getTotal()));

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

    public OrderView setOrderView() {
        Cart cart = cartService.getCart();
        OrderView orderView = new OrderView();
        List<CartItemUpdate> cartItemUpdateList = new ArrayList<>();
        cartItemUpdateList = cart.getCartItems().stream()
                .map(x -> new CartItemUpdate(x.getPhone().getId(), x.getQuantity()))
                .collect(Collectors.toList());
        //cart.getCartItems().forEach(x -> cartItemUpdateList.add(new CartItemUpdate(x.getPhone().getId(), x.getQuantity())));
        orderView.setCartItemUpdates(cartItemUpdateList);
        orderView.setSubtotal(cart.getTotal());
        orderView.setDelivery(deliveryPrice);
        orderView.setTotal(deliveryPrice.add(cart.getTotal()));
        return orderView;
    }

    public void updateStock(OrderView orderView) {
        Cart cart = cartService.getCart();
        for (CartItemUpdate cartItemUpdate : orderView.getCartItemUpdates()) {
            cart.getCartItems().stream()
                    .filter(x -> x.getPhone().getId().equals(cartItemUpdate.getId()))
                    .map(CartItem::getQuantity)
                    .findAny().ifPresent(cartItemUpdate::setQuantity);
        }
    }
}
