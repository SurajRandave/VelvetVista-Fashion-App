package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clothstoreapp.Adapter.CartAdapter;
import com.example.clothstoreapp.models.Cart;
import com.example.clothstoreapp.models.CartItem;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private TextView totalPriceTextView;
    private Button proceedToCheckoutButton;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        proceedToCheckoutButton = findViewById(R.id.checkoutButton);

        // Get cart items from the Cart class
        List<CartItem> cartItems = Cart.getCartItems();

        // Set up the adapter for ListView
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Calculate the total price and update UI
        updateTotalPrice();

        // Set item click listener to remove an item from the cart (Optional feature)
        cartListView.setOnItemClickListener((parent, view, position, id) -> {
            CartItem selectedItem = cartItems.get(position);
            Cart.removeItem(selectedItem); // Remove the selected item from the cart
            cartAdapter.notifyDataSetChanged(); // Refresh the ListView

            // Update the total price after removing the item
            updateTotalPrice();

            // Show a message indicating the item was removed
            Toast.makeText(CartActivity.this, "Item removed from cart", Toast.LENGTH_SHORT).show();
        });

        // Set click listener for the "Proceed to Checkout" button
        proceedToCheckoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Your cart is empty. Add items to proceed.", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to the CheckoutActivity
                Intent checkoutIntent = new Intent(CartActivity.this, Checkout.class);
                startActivity(checkoutIntent);
            }
        });
    }

    // Method to calculate and update the total price of all items in the cart
    private void updateTotalPrice() {
        double totalPrice = 0;
        List<CartItem> cartItems = Cart.getCartItems();

        // Calculate total price by summing up the prices of each item
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
        }

        // Update the TextView to display the total price
        totalPriceTextView.setText("Total Price: $" + String.format("%.2f", totalPrice));
    }
}
