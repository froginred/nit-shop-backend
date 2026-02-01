package com.example.security.controller.store;

import com.example.security.model.Favorite;
import com.example.security.service.store.FavoriteService;
import com.example.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteController {

    @Autowired private FavoriteService favoriteService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/{itemId}")
    public String add(
            @RequestHeader("Authorization") String token,
            @PathVariable Long itemId
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return favoriteService.add(username, itemId);
    }

    @DeleteMapping("/{itemId}")
    public String remove(
            @RequestHeader("Authorization") String token,
            @PathVariable Long itemId
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return favoriteService.remove(username, itemId);
    }

    @GetMapping
    public List<Favorite> list(
            @RequestHeader("Authorization") String token
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return favoriteService.getUserFavorites(username);
    }
}
