package com.example.clothstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clothstoreapp.models.Cart;

import java.util.Calendar;

public class OnlinePaymentActivity extends AppCompatActivity {

    private EditText creditCardNumberEditText, expiryDateEditText, cvcEditText;
    private Button submitPaymentButton;
    private TextView totalAmountTextView;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);

        // Initialize views
        creditCardNumberEditText = findViewById(R.id.creditCardNumberEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        cvcEditText = findViewById(R.id.cvcEditText);
        submitPaymentButton = findViewById(R.id.submitPaymentButton);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        // Retrieve the total amount passed from the previous activity
        totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);
        totalAmountTextView.setText("Total Amount: $" + String.format("%.2f", totalAmount));

        // Enable/Disable Pay Now button based on input validation
        creditCardNumberEditText.addTextChangedListener(textWatcher);
        expiryDateEditText.addTextChangedListener(textWatcher);
        cvcEditText.addTextChangedListener(textWatcher);

        // Handle the payment submission
        submitPaymentButton.setOnClickListener(v -> {
            String cardNumber = creditCardNumberEditText.getText().toString();
            String expiryDate = expiryDateEditText.getText().toString();
            String cvc = cvcEditText.getText().toString();

            // Validate input
            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvc.isEmpty()) {
                Toast.makeText(OnlinePaymentActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!isValidCardNumber(cardNumber)) {
                Toast.makeText(OnlinePaymentActivity.this, "Invalid card number", Toast.LENGTH_SHORT).show();
            } else if (!isValidExpiryDate(expiryDate)) {
                Toast.makeText(OnlinePaymentActivity.this, "Invalid expiry date", Toast.LENGTH_SHORT).show();
            } else if (!isValidCVC(cvc)) {
                Toast.makeText(OnlinePaymentActivity.this, "Invalid CVC", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate payment processing
                processPayment(cardNumber, expiryDate, cvc);
            }
        });
    }

    // Basic card number validation (16 digits)
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("[0-9]{16}"); // Ensures it only contains 16 digits
    }

    // Basic expiry date validation (MM/YY format and check if the expiry date is not in the past)
    private boolean isValidExpiryDate(String expiryDate) {
        if (!expiryDate.matches("^(0[1-9]|1[0-2])\\/([0-9]{2})$")) {
            return false;
        }

        // Extract month and year
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        // Get current date and compare
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR) % 100; // Get last two digits of the year
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        return (year > currentYear) || (year == currentYear && month >= currentMonth);
    }

    // Basic CVC validation (3 digits)
    private boolean isValidCVC(String cvc) {
        return cvc.length() == 3;  // Example: validate length for simplicity
    }

    // Simulate a payment process
    private void processPayment(String cardNumber, String expiryDate, String cvc) {
        // Simulate a successful transaction
        Toast.makeText(this, "Payment of $" + String.format("%.2f", totalAmount) + " processed successfully", Toast.LENGTH_LONG).show();

        // Optionally, clear the cart here if necessary
        Cart.clearCart();  // Uncomment if you want to clear the cart after payment

        // After successful payment, navigate to the Order Confirmation screen
        Intent intent = new Intent(OnlinePaymentActivity.this, OrderConfirmedActivity.class);
        intent.putExtra("TOTAL_AMOUNT", totalAmount); // Pass the total amount to the confirmation screen
        startActivity(intent);

        finish();  // Close the payment activity
    }

    // TextWatcher to enable/disable the Pay Now button based on input validation
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            togglePayButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    // Toggle the Pay Now button based on the input validation
    private void togglePayButton() {
        String cardNumber = creditCardNumberEditText.getText().toString();
        String expiryDate = expiryDateEditText.getText().toString();
        String cvc = cvcEditText.getText().toString();

        // Enable the button if all fields are filled and valid
        submitPaymentButton.setEnabled(!cardNumber.isEmpty() && !expiryDate.isEmpty() && !cvc.isEmpty() &&
                isValidCardNumber(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVC(cvc));
    }
}
