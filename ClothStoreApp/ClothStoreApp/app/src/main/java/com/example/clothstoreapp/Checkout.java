package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.clothstoreapp.models.Cart;
import com.example.clothstoreapp.models.CartItem;
import java.util.List;

public class Checkout extends AppCompatActivity {

    private LinearLayout cartItemsContainer;
    private TextView totalPriceTextView;
    private Button updateAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize views
        cartItemsContainer = findViewById(R.id.cartItemsContainer);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        updateAddressButton = findViewById(R.id.updateAddressButton);

        // Get cart items from the Cart class
        List<CartItem> cartItems = Cart.getCartItems();

        // If cart is empty, show a message and disable the update button
        if (cartItems.isEmpty()) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("Your cart is empty.");
            emptyMessage.setGravity(Gravity.CENTER);
            cartItemsContainer.addView(emptyMessage);
            totalPriceTextView.setText("Total Price: $0.00");
            updateAddressButton.setEnabled(false);
            return;
        }

        // Display cart items and calculate the total price
        double totalPrice = displayCartItems(cartItems);

        // Handle update address button click
        updateAddressButton.setOnClickListener(v -> {
            // Pass the total amount to AddressActivity
            Intent intent = new Intent(Checkout.this, AddressActivity.class);
            intent.putExtra("TOTAL_AMOUNT", totalPrice);  // Pass the total amount
            startActivity(intent);
        });
    }

    // Method to display each cart item with image and details
    private double displayCartItems(List<CartItem> cartItems) {
        double totalPrice = 0;

        for (CartItem item : cartItems) {
            // Create a container for each cart item
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(8, 8, 8, 8);
            itemLayout.setGravity(Gravity.CENTER_VERTICAL);

            // Create an ImageView for the product image
            ImageView itemImageView = new ImageView(this);
            itemImageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            Glide.with(this).load(item.getImageUrl()).into(itemImageView);

            // Create a TextView for the product details (name and price)
            TextView itemDetailsTextView = new TextView(this);
            itemDetailsTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            itemDetailsTextView.setPadding(16, 0, 0, 0);
            itemDetailsTextView.setText(item.getTitle() + "\nPrice: $" + String.format("%.2f", item.getPrice()));

            // Add views to the item layout
            itemLayout.addView(itemImageView);
            itemLayout.addView(itemDetailsTextView);

            // Add the item layout to the cart items container
            cartItemsContainer.addView(itemLayout);

            // Calculate the total price
            totalPrice += item.getPrice();
        }

        // Set the total price in the TextView
        totalPriceTextView.setText("Total Price: $" + String.format("%.2f", totalPrice));

        // Return the total price
        return totalPrice;
    }
}
