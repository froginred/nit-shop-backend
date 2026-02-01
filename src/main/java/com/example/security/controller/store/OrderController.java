package com.example.security.controller.store;

import com.example.security.model.Order;
import com.example.security.model.OrderItem;
import com.example.security.service.store.OrderService;
import com.example.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/cart/items")
    public Order addToCart(
            @RequestHeader("Authorization") String token,
            @RequestParam Long itemId,
            @RequestParam int quantity
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return orderService.addItemToCart(username, itemId, quantity);
    }

    @DeleteMapping("/cart/items/{itemId}")
    public Order removeFromCart(
            @RequestHeader("Authorization") String token,
            @PathVariable Long itemId
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return orderService.removeItemFromCart(username, itemId);
    }

    @GetMapping("/cart")
    public List<OrderItem> viewCart(
            @RequestHeader("Authorization") String token
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return orderService.getCartItems(username);
    }

    @PostMapping("/checkout")
    public Order checkout(
            @RequestHeader("Authorization") String token,
            @RequestParam String shippingAddress
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return orderService.checkout(username, shippingAddress);
    }

    @GetMapping
    public List<Order> getOrders(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return orderService.listOrdersForUser(username);
    }

    @GetMapping("/{orderId}")
    public com.example.security.model.OrderDetails getOrderDetails(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderId
    ) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return orderService.getOrderDetails(username, orderId);
    }
}
