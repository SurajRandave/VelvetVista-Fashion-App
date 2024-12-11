package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clothstoreapp.models.Address;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText, stateEditText, postalCodeEditText;
    private Button confirmPurchaseButton;
    private TextView totalPriceTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference addressReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        addressReference = FirebaseDatabase.getInstance().getReference("user_addresses");

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cityEditText = findViewById(R.id.cityEditText);
        stateEditText = findViewById(R.id.stateEditText);
        postalCodeEditText = findViewById(R.id.postalCodeEditText);
        confirmPurchaseButton = findViewById(R.id.confirmPurchaseButton);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        // Get the total amount passed from CheckoutActivity
        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);

        // Display the total amount in the TextView
        totalPriceTextView.setText("Total Price: $" + String.format("%.2f", totalAmount));

        // Handle confirm purchase button click
        confirmPurchaseButton.setOnClickListener(v -> {
            // Check if user is authenticated
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                // User is not logged in, prompt them to log in
                Toast.makeText(AddressActivity.this, "Please log in to continue", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(AddressActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                return;  // Prevent saving the address if not authenticated
            }

            // Get user input
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String city = cityEditText.getText().toString();
            String state = stateEditText.getText().toString();
            String postalCode = postalCodeEditText.getText().toString();

            // Check if any field is empty
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || postalCode.isEmpty()) {
                Toast.makeText(AddressActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save the address data to Firebase
            String userId = currentUser.getUid();  // Get the current logged-in user's ID
            Address userAddress = new Address(name, phone, address, city, state, postalCode);

            // Store the address under the user's ID in the "user_addresses" node
            addressReference.child(userId).setValue(userAddress)
                    .addOnSuccessListener(aVoid -> {
                        // Data saved successfully
                        Toast.makeText(AddressActivity.this, "Address saved successfully!", Toast.LENGTH_SHORT).show();

                        // Pass the total amount to the PaymentModeActivity
                        Intent intent = new Intent(AddressActivity.this, PaymentMode.class);
                        intent.putExtra("TOTAL_AMOUNT", totalAmount);  // Pass the total amount
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Failed to save data
                        Toast.makeText(AddressActivity.this, "Error saving address: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
