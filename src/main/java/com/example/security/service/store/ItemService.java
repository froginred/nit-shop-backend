package com.example.security.service.store ;

import com.example.security.model.Item;
import com.example.security.repository.store.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> search(String text) {
        return itemRepository.searchByTitle(text);
    }

    public Item getById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        validateItem(item);
        // default category
        if (item.getItemCategory() == null) {
            item.setItemCategory(com.example.security.model.ItemCategory.GENERAL);
        }
        return itemRepository.create(item);
    }

    public Item updateItem(Long id, Item item) {
        Item existing = itemRepository.findById(id);
        if (existing == null) throw new RuntimeException("Item not found");
        item.setId(id);
        validateItem(item);
        if (item.getItemCategory() == null) {
            item.setItemCategory(existing.getItemCategory());
        }
        return itemRepository.update(id, item);
    }

    public Item updateStock(Long id, int stock) {
        if (stock < 0) throw new RuntimeException("Stock must be >= 0");
        Item existing = itemRepository.findById(id);
        if (existing == null) throw new RuntimeException("Item not found");
        return itemRepository.updateStock(id, stock);
    }

    public String deleteItem(Long id) {
        boolean deleted = itemRepository.delete(id);
        if (!deleted) return "Item not found";
        return "Item deleted successfully";
    }

    private void validateItem(Item item) {
        if (item == null) throw new RuntimeException("Item is required");
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) throw new RuntimeException("Title is required");
        if (item.getPrice() == null || item.getPrice().signum() < 0) throw new RuntimeException("Price must be >= 0");
        if (item.getStock() == null || item.getStock() < 0) throw new RuntimeException("Stock must be >= 0");
        // description optional, imageUrl optional, category optional
    }
}
