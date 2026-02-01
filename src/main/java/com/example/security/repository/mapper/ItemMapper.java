package com.example.security.repository.mapper;

import com.example.security.model.Item;
import com.example.security.model.ItemCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Item(
                rs.getLong("id"),
                ItemCategory.valueOf(rs.getString("item_category")),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("image_url"),
                rs.getBigDecimal("price"),
                rs.getInt("stock")
        );
    }
}
