package com.example.security.repository.store;

import com.example.security.model.Item;
import com.example.security.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ITEMS_TABLE = "items";

    public Item create(Item item) {
        String sql = String.format(
                "INSERT INTO %s (item_category, title, description, image_url, price, stock) VALUES (?,?,?,?,?,?)",
                ITEMS_TABLE
        );
        jdbcTemplate.update(sql,
                item.getItemCategory() == null ? "GENERAL" : item.getItemCategory().name(),
                item.getTitle(),
                item.getDescription(),
                item.getImageUrl(),
                item.getPrice(),
                item.getStock()
        );

        // H2 simple way: return the newest row (good enough for a student project)
        return jdbcTemplate.queryForObject(
                String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", ITEMS_TABLE),
                new ItemMapper()
        );
    }

    public Item findById(Long id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", ITEMS_TABLE);
            return jdbcTemplate.queryForObject(sql, new ItemMapper(), id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Item> findAll() {
        String sql = String.format("SELECT * FROM %s ORDER BY id", ITEMS_TABLE);
        return jdbcTemplate.query(sql, new ItemMapper());
    }

    public List<Item> searchByTitle(String text) {
        String sql = String.format("SELECT * FROM %s WHERE LOWER(title) LIKE ? ORDER BY id", ITEMS_TABLE);
        return jdbcTemplate.query(sql, new ItemMapper(), "%" + text.toLowerCase() + "%");
    }


    public Item update(Long id, Item item) {
        String sql = String.format(
                "UPDATE %s SET item_category = ?, title = ?, description = ?, image_url = ?, price = ?, stock = ? WHERE id = ?",
                ITEMS_TABLE
        );
        jdbcTemplate.update(sql,
                item.getItemCategory() == null ? "GENERAL" : item.getItemCategory().name(),
                item.getTitle(),
                item.getDescription(),
                item.getImageUrl(),
                item.getPrice(),
                item.getStock(),
                id
        );
        return findById(id);
    }

    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", ITEMS_TABLE);
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Item updateStock(Long id, int newStock) {
        String sql = String.format("UPDATE %s SET stock = ? WHERE id = ?", ITEMS_TABLE);
        jdbcTemplate.update(sql, newStock, id);
        return findById(id);
    }
}
