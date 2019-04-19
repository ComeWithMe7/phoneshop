package com.es.core.order;

import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private OrderDao orderDao;

    @Override
    public void placeOrder(Order order) {
        orderDao.save(order);
        for (OrderItem orderItem : order.getOrderItems()) {
            phoneDao.updateStock(orderItem.getPhone().getId(), phoneDao.getStock(orderItem.getPhone().getId()) - orderItem.getQuantity());
        }
        cartService.cleanCart();
    }

    @Override
    public Order getOrderByHash(String hash) {
        Optional<Order> optionalOrder = orderDao.get(hash);
        return optionalOrder.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderDao.getById(orderId);
        return optionalOrder.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        orderDao.updateStatus(orderId, status);
    }
}
