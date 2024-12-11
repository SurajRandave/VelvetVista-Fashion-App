package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.clothstoreapp.models.Cart;
import com.example.clothstoreapp.models.CartItem;

public class ProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Retrieve data passed from the MainActivity
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        double oldPrice = intent.getDoubleExtra("oldPrice", 0);
        double price = intent.getDoubleExtra("price", 0);
        double rating = intent.getDoubleExtra("rating", 0);
        int review = intent.getIntExtra("review", 0);

        // Find views in the layout
        ImageView productImageView = findViewById(R.id.productImageView);
        TextView titleTextView = findViewById(R.id.titleTxt);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView oldPriceTextView = findViewById(R.id.oldPriceTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        TextView ratingTextView = findViewById(R.id.ratingTextView);
        TextView reviewTextView = findViewById(R.id.reviewTextView);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        Button buyNowButton = findViewById(R.id.buynowbutton);

        // Set the data to the views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        oldPriceTextView.setText("Old Price: $" + oldPrice);
        priceTextView.setText("Price: $" + price);
        ratingTextView.setText("Rating: " + rating);
        reviewTextView.setText("Reviews: " + review);

        // Load the image using Glide
        Glide.with(this).load(imageUrl).into(productImageView);

        // Set click listener for Add to Cart button
        addToCartButton.setOnClickListener(v -> {
            // Create a new CartItem and add it to the Cart
            CartItem cartItem = new CartItem(title, price, imageUrl);
            Cart.addItem(cartItem);
            Toast.makeText(ProductDetail.this, "Item added to cart", Toast.LENGTH_SHORT).show();
        });

        // Set click listener for Buy Now button
        buyNowButton.setOnClickListener(v -> {
            // Navigate to the checkout page
            Intent checkoutIntent = new Intent(ProductDetail.this, Checkout.class);
            checkoutIntent.putExtra("product_title", title);
            checkoutIntent.putExtra("product_price", price);
            checkoutIntent.putExtra("product_imageUrl", imageUrl);
            startActivity(checkoutIntent);
        });
    }
}
