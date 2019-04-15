package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.PhoneDao;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private MessageSource messageSource;

    @Override
    public PlaceOrder placeOrder(String firstName, String lastName, String address, String phoneNumber, String information) {
        Cart cart = cartService.getCart();
        PlaceOrder placeOrder = new PlaceOrder();
        Map<Long, String> removedProducts = new HashMap<>();
        Map<Long, Long> productsForUpdate = new HashMap<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Long stock = phoneDao.getStock(cartItem.getPhone().getId());
            productsForUpdate.put(cartItem.getPhone().getId(), stock - cartItem.getQuantity());
            if (stock < cartItem.getQuantity()) {
                Long quantity = cartItem.getQuantity();
                cartService.update(cartItem.getPhone().getId(), stock);
                removedProducts.put(cartItem.getPhone().getId(), "The amount of this product was less from " + quantity + " to " + stock);
                placeOrder.setSuccessful(false);
            }
        }
        if (placeOrder.isSuccessful()) {
            Order order = createOrder(firstName, lastName, address, phoneNumber, information);
            orderDao.save(order);
            placeOrder.setHash(order.getHash());
            for (Map.Entry<Long, Long> entry : productsForUpdate.entrySet()) {
                phoneDao.updateStock(entry.getKey(), entry.getValue());
            }
            cartService.cleanCart();
        }
        placeOrder.setRemovedProducts(removedProducts);
        return placeOrder;
    }

    @Override
    public Order getOrder(String hash) {
        Optional<Order> optionalOrder = orderDao.get(hash);
        return optionalOrder.orElseThrow(() -> new OrderNotFoundException("Order with such" + hash + "wasn't founded"));
    }

    private Order createOrder(String firstName, String lastName, String address, String phoneNumber, String information) {
        Cart cart = cartService.getCart();
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setDeliveryAddress(address);
        order.setContactPhoneNo(phoneNumber);
        order.setInformation(information);
        order.setHash(UUID.randomUUID().toString());
        order.setSubtotal(cart.getTotal());
        BigDecimal deliveryPrice = BigDecimal.valueOf(Double.parseDouble(messageSource.getMessage("delivery.price", null, Locale.getDefault())));
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(cart.getTotal().add(deliveryPrice));

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(cartItem.getPhone());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return order;
    }
}
