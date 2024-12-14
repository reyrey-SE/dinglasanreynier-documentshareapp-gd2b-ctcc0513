package com.docsharing.documentshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.docsharing.documentshare.R;

public class LoginActivity extends AppCompatActivity {

    // Declare EditText fields for email and password
    EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  //  corresponds to layout file

        // Initialize EditText fields
        emailEditText = findViewById(R.id.editTextTextPersonName);  // Email EditText
        passwordEditText = findViewById(R.id.editTextTextPassword); // Password EditText

        // Set onClickListener for the Login button
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered in the EditText fields
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate email and password
                if (email.isEmpty() || password.isEmpty()) {
                    // Show a toast message if either email or password is empty
                    Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to the dashboard activity if email and password are not empty
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);  // DashboardActivity is defined
                    startActivity(intent);
                    finish();  // Close the LoginActivity after successful login
                }
            }
        });
    }
}
