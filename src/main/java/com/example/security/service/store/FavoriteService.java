package com.example.security.service.store;

import com.example.security.model.Favorite;
import com.example.security.repository.store.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public String add(String username, Long itemId) {
        Favorite existing = favoriteRepository.findByUserAndItem(username, itemId);
        if (existing != null) {
            return "Item already in favorites";
        }
        return favoriteRepository.addFavorite(username, itemId);
    }

    public String remove(String username, Long itemId) {
        return favoriteRepository.removeFavorite(username, itemId);
    }

    public List<Favorite> getUserFavorites(String username) {
        return favoriteRepository.findByUser(username);
    }
}
