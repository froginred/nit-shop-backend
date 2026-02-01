package com.example.security.repository.store;

import com.example.security.model.Favorite;
import com.example.security.repository.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FAVORITES_TABLE = "favorites";

    public String addFavorite(String userId, Long itemId) {
        String sql = String.format("INSERT INTO %s (user_id, item_id) VALUES (?,?)", FAVORITES_TABLE);
        jdbcTemplate.update(sql, userId, itemId);
        return "Favorite added";
    }

    public String removeFavorite(String userId, Long itemId) {
        String sql = String.format("DELETE FROM %s WHERE user_id = ? AND item_id = ?", FAVORITES_TABLE);
        jdbcTemplate.update(sql, userId, itemId);
        return "Favorite removed";
    }

    public List<Favorite> findByUser(String userId) {
        String sql = String.format("SELECT * FROM %s WHERE user_id = ? ORDER BY id", FAVORITES_TABLE);
        return jdbcTemplate.query(sql, new FavoriteMapper(), userId);
    }

    public Favorite findByUserAndItem(String userId, Long itemId) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE user_id = ? AND item_id = ?", FAVORITES_TABLE);
            return jdbcTemplate.queryForObject(sql, new FavoriteMapper(), userId, itemId);
        } catch (Exception e) {
            return null;
        }
    }
}
