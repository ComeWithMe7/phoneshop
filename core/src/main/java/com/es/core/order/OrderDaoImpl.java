package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    private String SQL_SAVE_ORDER = "insert into orders (firstName, lastName, deliveryAddress, contactPhoneNo," +
            " information, subtotal, deliveryPrice, totalPrice, hash) values (:firstName, :lastName, :deliveryAddress," +
            " :contactPhoneNo, :information, :subtotal, :deliveryPrice, :totalPrice, :hash)";

    private String SQL_SAVE_ORDER_ITEM = "insert into orderItem (phoneId, quantity, orderId) values (:phoneId, :quantity, :orderId)";

    private String SQL_GET_BY_HASH = "select * from orders where hash = :hash";

    private String SQL_GET_ORDER_ITEMS_BY_ID = "select * from orderItem where orderItem.orderId = :id";

    @Override
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);
        int status = namedParameterJdbcTemplate.update(SQL_SAVE_ORDER, namedParameters, keyHolder);
        if (status != 0) {
            order.setId(keyHolder.getKey().longValue());
            saveOrderItems(order);
        }
    }

    @Override
    public Optional<Order> get(String hash) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("hash", hash);
        try {
            Order order = (Order) namedParameterJdbcTemplate.queryForObject(SQL_GET_BY_HASH, namedParameters, new BeanPropertyRowMapper(Order.class));
            order.setOrderItems(getOrderItems(order.getId()));
            return Optional.of(order);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    private List<OrderItem> getOrderItems(Long orderId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", orderId);
        List<OrderItem> orderItems = new ArrayList<> ((Collection<? extends OrderItem>) namedParameterJdbcTemplate.query(SQL_GET_ORDER_ITEMS_BY_ID, namedParameters, new BeanPropertyRowMapper(OrderItem.class)));
        for (OrderItem orderItem : orderItems) {
            Optional<Phone> optionalPhone = phoneDao.getByOrderItem(orderItem.getId());
            if (optionalPhone.isPresent()) {
                orderItem.setPhone(optionalPhone.get());
            }
        }
        return orderItems;
    }

    private void saveOrderItems(Order order) {
        order.getOrderItems().forEach(x -> x.setOrderId(order.getId()));
        for (OrderItem orderItem : order.getOrderItems()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            Map<String, Long> namedParameters = new HashMap<>(3);
            namedParameters.put("phoneId", orderItem.getPhone().getId());
            namedParameters.put("quantity", orderItem.getQuantity());
            namedParameters.put("orderId", orderItem.getOrderId());
            int status = namedParameterJdbcTemplate.update(SQL_SAVE_ORDER_ITEM, new MapSqlParameterSource(namedParameters), keyHolder);
            if (status != 0) {
                orderItem.setId(keyHolder.getKey().longValue());
            }
        }
    }
}
