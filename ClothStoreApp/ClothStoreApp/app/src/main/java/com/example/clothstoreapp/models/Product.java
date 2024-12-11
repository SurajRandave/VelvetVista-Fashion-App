package com.example.clothstoreapp.models;

public class Product {

    private String title;
    private double price;
    private String imageUrl;  // Assuming this is the URL for the product image

    // Constructor
    public Product(String title, double price, String imageUrl) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
