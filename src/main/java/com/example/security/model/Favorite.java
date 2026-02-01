package com.example.security.model;

public class Favorite {
    private Long id;
    private String userId;   // username
    private Long itemId;

    public Favorite() {}

    public Favorite(Long id, String userId, Long itemId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }


}
