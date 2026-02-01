package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Item {
    private Long id;

    @JsonProperty(value = "item_category")
    private ItemCategory itemCategory;

    private String title;
    private String description;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    private BigDecimal price;
    private Integer stock;

    public Item() {}

    public Item(Long id, ItemCategory itemCategory, String title, String description, String imageUrl, BigDecimal price, Integer stock) {
        this.id = id;
        this.itemCategory = itemCategory;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ItemCategory getItemCategory() { return itemCategory; }
    public void setItemCategory(ItemCategory itemCategory) { this.itemCategory = itemCategory; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemCategory=" + itemCategory +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
