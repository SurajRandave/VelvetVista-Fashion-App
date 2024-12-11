package com.example.clothstoreapp.models;

import java.io.Serializable;

public class item implements Serializable {

    private String title;
    private String description;
    private double oldPrice;
    private double price;
    private double rating;
    private int review;
    private String[] picUrls;

    // Constructor
    public item(String title, String description, double oldPrice, double price, double rating, int review, String[] picUrls) {
        this.title = title;
        this.description = description;
        this.oldPrice = oldPrice;
        this.price = price;
        this.rating = rating;
        this.review = review;
        this.picUrls = picUrls;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getReview() {
        return review;
    }

    public String[] getPicUrls() {
        return picUrls;
    }
}
