package com.example.clothstoreapp.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    // Static list to hold all cart items
    private static List<CartItem> cartItems = new ArrayList<>();

    // Method to get all cart items (static so it can be accessed without an instance)
    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    // Static method to add a new item to the cart
    public static void addItem(CartItem item) {
        cartItems.add(item);
    }

    // Static method to remove an item from the cart
    public static void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    // Method to get the total price of the items in the cart
    public static double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice();  // Assuming CartItem has a getPrice() method
        }
        return total;
    }

    // Static method to clear the cart
    public static void clearCart() {
        cartItems.clear();
    }
}
