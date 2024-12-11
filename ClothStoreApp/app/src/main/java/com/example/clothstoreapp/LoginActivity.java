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

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private MaterialButton loginButton;
    private TextView registerLink;  // Declare register link
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);  // Register link view

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Check for empty fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return; // Don't proceed if fields are empty
            }

            // Attempt to sign in with email and password using Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // If sign-in is successful, navigate to MainActivity
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Navigate to MainActivity
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish(); // Close the login activity to prevent going back to it
                            }
                        } else {
                            // If sign-in fails, show an error message
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Authentication failed";
                            Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Handle register link click to navigate to RegistrationActivity
        registerLink.setOnClickListener(v -> {
            // Navigate to the RegistrationActivity when clicked
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is already logged in and navigate to MainActivity if so
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // If user is already logged in, go to the main screen
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // Close the login activity
        }
    }
}
