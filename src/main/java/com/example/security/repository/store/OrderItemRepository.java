package com.example.security.repository.store;

import com.example.security.model.OrderItem;
import com.example.security.repository.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ORDER_ITEMS_TABLE = "order_items";

    public OrderItem addItem(OrderItem oi) {
        String sql = String.format(
                "INSERT INTO %s (order_id, item_id, quantity, price) VALUES (?,?,?,?)",
                ORDER_ITEMS_TABLE
        );
        jdbcTemplate.update(sql, oi.getOrderId(), oi.getItemId(), oi.getQuantity(), oi.getPrice());

        return jdbcTemplate.queryForObject(
                String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", ORDER_ITEMS_TABLE),
                new OrderItemMapper()
        );
    }

    public void updateQuantity(Long orderId, Long itemId, int quantity) {
        String sql = String.format("UPDATE %s SET quantity = ? WHERE order_id = ? AND item_id = ?", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, quantity, orderId, itemId);
    }

    public List<OrderItem> findByOrder(Long orderId) {
        String sql = String.format("SELECT * FROM %s WHERE order_id = ? ORDER BY id", ORDER_ITEMS_TABLE);
        return jdbcTemplate.query(sql, new OrderItemMapper(), orderId);
    }

    public void deleteItem(Long orderId, Long itemId) {
        String sql = String.format("DELETE FROM %s WHERE order_id = ? AND item_id = ?", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, orderId, itemId);
    }

    public void deleteByOrder(Long orderId) {
        String sql = String.format("DELETE FROM %s WHERE order_id = ?", ORDER_ITEMS_TABLE);
        jdbcTemplate.update(sql, orderId);
    }
}
