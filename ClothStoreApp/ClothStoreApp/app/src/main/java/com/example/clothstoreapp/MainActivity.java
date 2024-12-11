package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.clothstoreapp.models.Cart;
import com.example.clothstoreapp.models.CartItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Cart cart; // Initialize Cart object
    private FirebaseAuth mAuth; // Firebase Auth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Check if the user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not logged in, navigate to LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Close MainActivity to prevent going back to it
            return;
        }

        // Initialize GridLayout and Cart
        gridLayout = findViewById(R.id.GridLayout);
        cart = new Cart(); // Create new Cart instance

        // Set up the FloatingActionButton (Cart button)
        FloatingActionButton cartFab = findViewById(R.id.cart_fab);
        cartFab.setOnClickListener(v -> {
            // Open CartActivity when the cart icon is clicked
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Load JSON data from Assets
        loadItemsFromAssets();
    }

    private void loadItemsFromAssets() {
        try {
            // Open the JSON file from the Assets folder
            InputStream inputStream = getAssets().open("item.json");

            // Convert InputStream to String
            String jsonString = convertStreamToString(inputStream);

            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray itemsArray = jsonObject.getJSONArray("item");

            // Add items to the GridLayout dynamically
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                String title = item.getString("title");
                String picUrl = item.getString("picUrl"); // Correctly parse picUrl as a String
                double price = item.getDouble("price");
                String description = item.getString("description"); // Add description
                double oldPrice = item.optDouble("oldPrice", 0); // Add oldPrice
                double rating = item.optDouble("rating", 0); // Add rating
                int review = item.optInt("review", 0); // Add reviews

                // Construct the full path for the asset image
                String imageUrl = "file:///android_asset/" + picUrl;

                // Dynamically create the item view (you can customize this)
                GridLayout gridItem = createGridItem(title, imageUrl, price);

                // Add the item view to the grid
                gridItem.setOnClickListener(v -> {
                    // Create an Intent to go to the ProductDetail Activity
                    Intent intent = new Intent(MainActivity.this, ProductDetail.class);

                    // Pass the item's details (title, description, image URL, oldPrice, price, rating, and review)
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("oldPrice", oldPrice);
                    intent.putExtra("price", price);
                    intent.putExtra("rating", rating);
                    intent.putExtra("review", review);

                    startActivity(intent);
                });

                gridLayout.addView(gridItem);
            }

        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load items", Toast.LENGTH_SHORT).show();
        }
    }

    private GridLayout createGridItem(String title, String imageUrl, double price) {
        // Create a new GridLayout to hold the individual item content
        GridLayout gridItemLayout = new GridLayout(this);
        gridItemLayout.setOrientation(GridLayout.VERTICAL);
        gridItemLayout.setColumnCount(1); // For individual item, 1 column

        // Add an ImageView to the GridItem
        ImageView imageView = new ImageView(this);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.paceholder) // Optional placeholder image
                .error(R.drawable.error_image) // Optional error image
                .into(imageView);
        gridItemLayout.addView(imageView);

        // Add a TextView for the title
        TextView titleTextView = new TextView(this);
        titleTextView.setText(title);
        titleTextView.setTextSize(18);
        gridItemLayout.addView(titleTextView);

        // Add a TextView for the price
        TextView priceTextView = new TextView(this);
        priceTextView.setText("Price: $" + price);
        priceTextView.setTextSize(16);
        gridItemLayout.addView(priceTextView);

        return gridItemLayout;
    }

    private String convertStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
