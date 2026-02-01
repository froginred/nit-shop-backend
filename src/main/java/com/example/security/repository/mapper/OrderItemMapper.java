package com.example.security.repository.mapper;

import com.example.security.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItem(
                rs.getLong("id"),
                rs.getLong("order_id"),
                rs.getLong("item_id"),
                rs.getInt("quantity"),
                rs.getBigDecimal("price")
        );
    }
}
