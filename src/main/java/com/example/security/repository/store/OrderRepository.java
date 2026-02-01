package com.example.security.repository.store;

import com.example.security.model.Order;
import com.example.security.model.OrderStatus;
import com.example.security.repository.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ORDERS_TABLE = "orders";

    public Order createTempOrder(String userId, String shippingAddress) {
        String sql = String.format(
                "INSERT INTO %s (user_id, shipping_address, total_price, status) VALUES (?,?,?,?)",
                ORDERS_TABLE
        );
        jdbcTemplate.update(sql, userId, shippingAddress, BigDecimal.ZERO, OrderStatus.TEMP.name());

        // H2 simple way: return newest row
        return jdbcTemplate.queryForObject(
                String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", ORDERS_TABLE),
                new OrderMapper()
        );
    }

    public Order findById(Long id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", ORDERS_TABLE);
            return jdbcTemplate.queryForObject(sql, new OrderMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Order> findByUser(String userId) {
        String sql = String.format("SELECT * FROM %s WHERE user_id = ? ORDER BY order_date DESC", ORDERS_TABLE);
        return jdbcTemplate.query(sql, new OrderMapper(), userId);
    }

    public Order updateTotalsAndStatus(Long orderId, BigDecimal totalPrice, OrderStatus status) {
        String sql = String.format("UPDATE %s SET total_price = ?, status = ? WHERE id = ?", ORDERS_TABLE);
        jdbcTemplate.update(sql, totalPrice, status.name(), orderId);
        return findById(orderId);
    }

    public Order updateTotalsStatusAndAddress(Long orderId, BigDecimal totalPrice, OrderStatus status, String shippingAddress) {
        String sql = String.format("UPDATE %s SET total_price = ?, status = ?, shipping_address = ? WHERE id = ?", ORDERS_TABLE);
        jdbcTemplate.update(sql, totalPrice, status.name(), shippingAddress, orderId);
        return findById(orderId);
    }

    public void delete(Long orderId) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", ORDERS_TABLE);
        jdbcTemplate.update(sql, orderId);
    }

    public List<Order> findByUserTempFirst(String userId) {
        String sql = String.format(
                "SELECT * FROM %s WHERE user_id = ? ORDER BY CASE WHEN status = 'TEMP' THEN 0 ELSE 1 END, order_date DESC",
                ORDERS_TABLE
        );
        return jdbcTemplate.query(sql, new OrderMapper(), userId);
    }

    public Order findByIdAndUser(Long id, String userId) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ? AND user_id = ?", ORDERS_TABLE);
            return jdbcTemplate.queryForObject(sql, new OrderMapper(), id, userId);
        } catch (Exception e) {
            return null;
        }
    }

    public Order findTempOrderByUser(String userId) {
        try {
            String sql = String.format(
                    "SELECT * FROM %s WHERE user_id = ? AND status = 'TEMP' ORDER BY id DESC LIMIT 1",
                    ORDERS_TABLE
            );
            return jdbcTemplate.queryForObject(sql, new OrderMapper(), userId);
        } catch (Exception e) {
            return null;
        }
    }

}
