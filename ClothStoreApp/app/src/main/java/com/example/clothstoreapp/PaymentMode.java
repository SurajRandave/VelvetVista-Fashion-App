package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentMode extends AppCompatActivity {

    private RadioGroup paymentMethodRadioGroup;
    private RadioButton onlinePaymentRadioButton;
    private RadioButton cashOnDeliveryRadioButton;
    private Button proceedButton;
    private TextView totalAmountTextView;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

        // Initialize views
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        onlinePaymentRadioButton = findViewById(R.id.radio_online_payment);
        cashOnDeliveryRadioButton = findViewById(R.id.radio_cash_on_delivery);
        proceedButton = findViewById(R.id.proceedButton);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        // Retrieve total amount passed from the previous activity
        totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);
        totalAmountTextView.setText("Total Amount: $" + String.format("%.2f", totalAmount));

        // Handle proceed button click
        proceedButton.setOnClickListener(v -> {
            // Check if a payment method is selected
            int selectedId = paymentMethodRadioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                // If no payment method is selected, show a message
                Toast.makeText(PaymentMode.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            } else {
                if (selectedId == R.id.radio_online_payment) {
                    // If Online Payment is selected, go to Online Payment Activity
                    Toast.makeText(PaymentMode.this, "Proceeding to online payment", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentMode.this, OnlinePaymentActivity.class);
                    intent.putExtra("TOTAL_AMOUNT", totalAmount);
                    startActivity(intent);
                } else if (selectedId == R.id.radio_cash_on_delivery) {
                    // If Cash on Delivery is selected, confirm the order and pass the amount
                    Toast.makeText(PaymentMode.this, "Order confirmed with Cash on Delivery", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentMode.this, OrderConfirmedActivity.class);
                    intent.putExtra("TOTAL_AMOUNT", totalAmount);  // Pass total amount here
                    startActivity(intent);
                }
            }
        });
    }
}
