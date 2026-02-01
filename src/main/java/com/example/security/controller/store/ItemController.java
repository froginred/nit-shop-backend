package com.example.security.controller.store;

import com.example.security.model.Item;
import com.example.security.service.store.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String q) {
        return itemService.search(q);
    }
}
