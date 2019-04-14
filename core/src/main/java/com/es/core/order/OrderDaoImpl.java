package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.ColorDao;
import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDaoImpl implements OrderDao {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private ColorDao colorDao;

    private OrderRowMapper orderRowMapper = new OrderRowMapper();

    private OrderItemRowMapper orderItemRowMapper = new OrderItemRowMapper();

    private PhoneRowMapper phoneRowMapper = new PhoneRowMapper();


    private String SQL_SAVE_ORDER = "insert into orders (firstName, lastName, deliveryAddress, contactPhoneNo," +
            " information, subtotal, deliveryPrice, totalPrice, hash, status) values (:firstName, :lastName, :deliveryAddress," +
            " :contactPhoneNo, :information, :subtotal, :deliveryPrice, :totalPrice, :hash, :status)";

    private String SQL_SAVE_ORDER_ITEM = "insert into orderItem (phoneId, quantity, orderId) values (:phoneId, :quantity, :orderId)";

    private String SQL_GET_BY_HASH = "SELECT * FROM orders JOIN orderItem ON orders.id = orderItem.orderId JOIN phones ON orderItem.phoneId = phones.id " +
            "WHERE hash = :hash";

    private String SQL_GET_ORDER_ITEMS_ID = "select id from orderItem WHERE orderId = :orderId";

    private String SQL_FIND_ALL = "select * from orders";

    private String SQL_GET_BY_ID = "SELECT * FROM orders JOIN orderItem ON orders.id = orderItem.orderId JOIN phones ON orderItem.phoneId = phones.id " +
            "WHERE orders.id = :id";

    private String SQL_UPDATE_STATUS = "update orders set status = :status where id = :id";

    @Override
    @Transactional
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        AbstractSqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);
        namedParameters.registerSqlType("status", Types.VARCHAR);
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
            Order foundedOrder = namedParameterJdbcTemplate.query(SQL_GET_BY_HASH, namedParameters, resultSet -> {
            Order order = new Order();
            List<OrderItem> orderItems = new ArrayList<>();
            while (resultSet.next()){
                if (resultSet.isFirst()) {
                    order = orderRowMapper.mapRow(resultSet, 1);
                }
                Phone phone = phoneRowMapper.mapRow(resultSet, resultSet.getRow());
                OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, resultSet.getRow());
                orderItem.setPhone(phone);
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);
            return order;
        });
            return Optional.of(foundedOrder);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAll() {
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public Optional<Order> getById(Long orderId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", orderId);
        try {
            Order foundedOrder = namedParameterJdbcTemplate.query(SQL_GET_BY_ID, namedParameters, resultSet -> {
                Order order = new Order();
                List<OrderItem> orderItems = new ArrayList<>();
                while (resultSet.next()){
                    if (resultSet.isFirst()) {
                        order = orderRowMapper.mapRow(resultSet, 1);
                    }
                    Phone phone = phoneRowMapper.mapRow(resultSet, resultSet.getRow());
                    OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, resultSet.getRow());
                    orderItem.setPhone(phone);
                    orderItems.add(orderItem);
                }
                order.setOrderItems(orderItems);
                return order;
            });
            return Optional.of(foundedOrder);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("status", status.name());
        parameterSource.addValue("id", orderId);
        namedParameterJdbcTemplate.update(SQL_UPDATE_STATUS, parameterSource);
    }

    private void saveOrderItems(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(x -> x.setOrderId(order.getId()));

        MapSqlParameterSource[] mapSqlParameterSources = orderItems.stream().map(orderItem -> {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("phoneId", orderItem.getPhone().getId());
            parameterSource.addValue("quantity", orderItem.getQuantity());
            parameterSource.addValue("orderId", orderItem.getOrderId());
            return parameterSource;
        }).toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(SQL_SAVE_ORDER_ITEM, mapSqlParameterSources);

        namedParameterJdbcTemplate.query(SQL_GET_ORDER_ITEMS_ID, new MapSqlParameterSource("orderId", order.getId()), resultSet -> {
            for (OrderItem orderItem : orderItems) {
                orderItem.setId(resultSet.getLong("id"));
                resultSet.next();
            }
        });
    }

    private class OrderRowMapper implements RowMapper<Order> {
        private static final String TABLE_NAME = "orders";

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            Order order = new Order();
            order.setId(resultSet.getLong(TABLE_NAME + ".id"));
            order.setHash(resultSet.getString("hash"));
            order.setFirstName(resultSet.getString("firstName"));
            order.setLastName(resultSet.getString("lastName"));
            order.setInformation(resultSet.getString("information"));
            order.setSubtotal(resultSet.getBigDecimal("subtotal"));
            order.setDeliveryPrice(resultSet.getBigDecimal("deliveryPrice"));
            order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
            order.setDeliveryAddress(resultSet.getString("deliveryAddress"));
            order.setContactPhoneNo(resultSet.getString("contactPhoneNo"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            return order;
        }
    }

    private class OrderItemRowMapper implements RowMapper<OrderItem> {

        @Override
        public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(resultSet.getLong("orderItem.id"));
            orderItem.setQuantity(resultSet.getLong("quantity"));
            return orderItem;
        }
    }

    private class PhoneRowMapper implements RowMapper<Phone> {
        private static final String TABLE_NAME = "phones";

        @Override
        public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
            Phone phone = new Phone();

            phone.setId(resultSet.getLong(TABLE_NAME + ".id"));
            phone.setBrand(resultSet.getString("brand"));
            phone.setModel(resultSet.getString("model"));
            phone.setPrice(resultSet.getBigDecimal("price"));

            phone.setDisplaySizeInches(resultSet.getBigDecimal("displaySizeInches"));
            phone.setWeightGr(resultSet.getInt("weightGr"));
            phone.setLengthMm(resultSet.getBigDecimal("lengthMm"));
            phone.setWidthMm(resultSet.getBigDecimal("widthMm"));
            phone.setHeightMm(resultSet.getBigDecimal("heightMm"));
            phone.setAnnounced(resultSet.getDate("announced"));
            phone.setDeviceType(resultSet.getString("deviceType"));
            phone.setOs(resultSet.getString("os"));

            phone.setDisplayResolution(resultSet.getString("displayResolution"));
            phone.setPixelDensity(resultSet.getInt("pixelDensity"));
            phone.setDisplayTechnology(resultSet.getString("displayTechnology"));
            phone.setBackCameraMegapixels(resultSet.getBigDecimal("backCameraMegapixels"));
            phone.setFrontCameraMegapixels(resultSet.getBigDecimal("frontCameraMegapixels"));
            phone.setRamGb(resultSet.getBigDecimal("ramGb"));
            phone.setInternalStorageGb(resultSet.getBigDecimal("internalStorageGb"));
            phone.setBatteryCapacityMah(resultSet.getInt("batteryCapacityMah"));
            phone.setTalkTimeHours(resultSet.getBigDecimal("talkTimeHours"));
            phone.setStandByTimeHours(resultSet.getBigDecimal("standByTimeHours"));
            phone.setBluetooth(resultSet.getString("bluetooth"));
            phone.setPositioning(resultSet.getString("positioning"));
            phone.setImageUrl(resultSet.getString("imageUrl"));
            phone.setDescription(resultSet.getString("description"));

            return phone;
        }
    }

}
