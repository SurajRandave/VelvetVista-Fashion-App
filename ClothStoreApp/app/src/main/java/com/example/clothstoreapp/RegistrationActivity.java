package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.button.MaterialButton;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, mobileEditText, addressEditText;
    private MaterialButton registerButton;
    private TextView loginLink;  // Declare login link
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        addressEditText = findViewById(R.id.addressEditText);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        // Handle register button click
        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String mobile = mobileEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();

            // Input validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Firebase authentication to register the user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Registration successful, navigate back to LoginActivity
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                finish(); // Close the registration activity
                            } else {
                                // Registration failed, show error message
                                Toast.makeText(RegistrationActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Handle login link click to navigate to LoginActivity
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();  // Close registration activity
        });
    }
}
