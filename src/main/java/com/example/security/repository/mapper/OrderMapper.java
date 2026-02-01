package com.example.security.repository.mapper;

import com.example.security.model.Order;
import com.example.security.model.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime dt = rs.getTimestamp("order_date").toLocalDateTime();

        return new Order(
                rs.getLong("id"),
                rs.getString("user_id"),
                dt,
                rs.getString("shipping_address"),
                rs.getBigDecimal("total_price"),
                OrderStatus.valueOf(rs.getString("status"))
        );
    }
}
