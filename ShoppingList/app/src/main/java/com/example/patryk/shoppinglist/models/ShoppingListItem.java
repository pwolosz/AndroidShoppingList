package com.example.patryk.shoppinglist.models;


public class ShoppingListItem {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    public ShoppingListItem(String description, String name, int id, String imageUrl) {
        this.description = description;
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }
}
