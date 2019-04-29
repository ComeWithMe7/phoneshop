package com.es.core.service;

import com.es.core.dao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.dao.PhoneDao;
import com.es.core.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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
        Map<Long, Long> updatedStock = order.getOrderItems().stream().collect(Collectors.toMap(x -> x.getPhone().getId(), x -> phoneDao.getStock(x.getPhone().getId()) - x.getQuantity()));
        phoneDao.updateStock(updatedStock);
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
