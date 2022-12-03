package com.example.drinkandcake.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id, name;
    private float price;
    private int quantity;
    private String image;
    private String category;

    public Product() {
    }

    public Product(String id, String name, float price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public Product(String name, float price, String image,String category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Product(String name, float price, String image, int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Product(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
