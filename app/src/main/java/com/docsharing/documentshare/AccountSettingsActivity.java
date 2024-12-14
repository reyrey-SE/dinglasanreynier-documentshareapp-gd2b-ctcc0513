package com.docsharing.documentshare;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AccountSettingsActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Account Settings");
        setSupportActionBar(toolbar);

        // Enable the "up" button for navigation back to the previous screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Bind UI components
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextEmail = findViewById(R.id.edit_text_email);
        buttonSave = findViewById(R.id.button_save);

        // Load saved account data (placeholder logic)
        loadAccountData();

        // Set up save button listener
        buttonSave.setOnClickListener(v -> saveAccountData());
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the "up" button click
        finish(); // Close this activity and return to the previous one
        return true;
    }

    private void loadAccountData() {
        // Placeholder logic for loading account data
        // Replace this with data from SharedPreferences or a database
        editTextUsername.setText("User123"); // Example username
        editTextEmail.setText("user@example.com"); // Example email
    }

    private void saveAccountData() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Save the account data (placeholder logic)
            // Replace this with SharedPreferences or database saving
            Toast.makeText(this, "Account data saved successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
