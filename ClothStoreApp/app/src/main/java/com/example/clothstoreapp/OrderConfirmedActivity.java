package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmedActivity extends AppCompatActivity {

    private TextView orderConfirmationTextView;
    private TextView totalAmountTextView;
    private Button goHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        // Initialize views
        orderConfirmationTextView = findViewById(R.id.orderConfirmationTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView); // TextView for total amount
        goHomeButton = findViewById(R.id.goHomeButton); // Use correct ID for the button

        // Get the total amount passed from the previous activity
        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);

        // Set the confirmation message
        orderConfirmationTextView.setText("Your order has been confirmed!");

        // Show the total amount
        totalAmountTextView.setText("Total Amount: $" + String.format("%.2f", totalAmount));

        // Set up button to go back to home page
        goHomeButton.setOnClickListener(v -> goToHome());
    }

    // Method to navigate back to home screen
    public void goToHome() {
        // Navigate to the MainActivity (or your desired activity)
        Intent intent = new Intent(OrderConfirmedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Optionally finish the current activity so user can't go back to it
    }
}
