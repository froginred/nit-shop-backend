package com.example.security.repository.mapper;

import com.example.security.model.Favorite;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteMapper implements RowMapper<Favorite> {
    @Override
    public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Favorite(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getLong("item_id")
        );
    }
}
