package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmedActivity extends AppCompatActivity {

    private TextView orderConfirmationTextView;
    private TextView totalAmountTextView;
    private Button goHomeButton;
    private String userPhoneNumber; // Store the user's phone number

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
        // Get the user's phone number passed from the previous activity
        userPhoneNumber = getIntent().getStringExtra("USER_PHONE_NUMBER");

        // Set the confirmation message
        orderConfirmationTextView.setText("Your order has been confirmed!");

        // Show the total amount
        totalAmountTextView.setText("Total Amount: $" + String.format("%.2f", totalAmount));

        // Set up button to go back to home page
        goHomeButton.setOnClickListener(v -> goToHome());

        // Send the SMS after confirming the order
        sendOrderConfirmationSMS(userPhoneNumber);
    }

    // Method to navigate back to home screen
    public void goToHome() {
        // Navigate to the MainActivity (or your desired activity)
        Intent intent = new Intent(OrderConfirmedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Optionally finish the current activity so user can't go back to it
    }

    // Method to send SMS to the user
    public void sendOrderConfirmationSMS(String phoneNumber) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String message = "Hi, this is Admin from Cloth Store App. Thank you for visiting our app. " +
                    "Your order has been successfully confirmed. Your product will be delivered " +
                    "within 7 working days. Please stay tuned. If you have any issues, contact us at " +
                    "7620653019 or email: surajrandave3@gmail.com.";

            // Send the SMS
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
