package com.example.security.service.store;

import com.example.security.model.Item;
import com.example.security.model.Order;
import com.example.security.model.OrderItem;
import com.example.security.model.OrderStatus;
import com.example.security.repository.store.ItemRepository;
import com.example.security.repository.store.OrderItemRepository;
import com.example.security.repository.store.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private ItemRepository itemRepository;

    /**
     * Enforces "one TEMP order per user" at the service level:
     * - If exists, return it
     * - If not, create it
     */
    public Order findTempOrder(String username) {
        return orderRepository.findTempOrderByUser(username);
    }

    public Order getOrCreateTempOrder(String username) {
        Order existing = findTempOrder(username);
        if (existing != null) return existing;

        return orderRepository.createTempOrder(username, null);
    }


    public List<OrderItem> getCartItems(String username) {
        Order order = findTempOrder(username);
        if (order == null) {
            return List.of();
        }
        return orderItemRepository.findByOrder(order.getId());
    }

    @Transactional
    public Order addItemToCart(String username, Long itemId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Quantity must be positive");

        Order order = getOrCreateTempOrder(username);
        Item item = itemRepository.findById(itemId);

        if (item == null) throw new RuntimeException("Item not found");

        // check total desired quantity (existing + new)
        List<OrderItem> existing = orderItemRepository.findByOrder(order.getId());
        int currentQty = 0;
        for (OrderItem oi : existing) {
            if (oi.getItemId().equals(itemId)) {
                currentQty = oi.getQuantity();
                break;
            }
        }
        int newQty = currentQty + quantity;

        if (item.getStock() < newQty) {
            throw new RuntimeException("Not enough stock");
        }

        if (currentQty == 0) {
            orderItemRepository.addItem(new OrderItem(null, order.getId(), itemId, newQty, item.getPrice()));
        } else {
            orderItemRepository.updateQuantity(order.getId(), itemId, newQty);
        }

        recalcOrderTotal(order.getId());
        return orderRepository.findById(order.getId());
    }

    @Transactional
    public Order removeItemFromCart(String username, Long itemId) {
        Order order = findTempOrder(username);
        if (order == null) {
            throw new RuntimeException("Cart is empty");
        }

        orderItemRepository.deleteItem(order.getId(), itemId);

        if (orderItemRepository.findByOrder(order.getId()).isEmpty()) {
            orderRepository.delete(order.getId());
            return null;
        }

        recalcOrderTotal(order.getId());
        return orderRepository.findById(order.getId());
    }

    @Transactional
    public Order checkout(String username, String shippingAddress) {
        Order order = findTempOrder(username);
        if (order == null) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> items = orderItemRepository.findByOrder(order.getId());
        if (items.isEmpty()) {
            orderRepository.delete(order.getId()); // optional cleanup
            throw new RuntimeException("Cart is empty");
        }

        // Validate stock at payment time
        for (OrderItem oi : items) {
            Item item = itemRepository.findById(oi.getItemId());
            if (item == null || item.getStock() < oi.getQuantity()) {
                throw new RuntimeException("Not enough stock for item " + oi.getItemId());
            }
        }

        // Decrease stock
        for (OrderItem oi : items) {
            Item item = itemRepository.findById(oi.getItemId());
            itemRepository.updateStock(item.getId(), item.getStock() - oi.getQuantity());
        }

        BigDecimal total = recalcOrderTotal(order.getId());

        Order updated = orderRepository.updateTotalsStatusAndAddress(order.getId(), total, OrderStatus.CLOSE, shippingAddress);

        // After checkout, user can create a NEW temp order when needed.
        return updated;
    }

    private BigDecimal recalcOrderTotal(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrder(orderId);
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem oi : items) {
            total = total.add(oi.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())));
        }
        orderRepository.updateTotalsAndStatus(orderId, total, OrderStatus.TEMP);
        return total;
    }

    public List<Order> listOrdersForUser(String username) {
        return orderRepository.findByUserTempFirst(username);
    }

    public com.example.security.model.OrderDetails getOrderDetails(String username, Long orderId) {
        Order order = orderRepository.findByIdAndUser(orderId, username);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        List<OrderItem> items = orderItemRepository.findByOrder(order.getId());
        return new com.example.security.model.OrderDetails(order, items);
    }
}
