package com.docsharing.documentshare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewName, textViewEmail;
    private EditText editTextName, editTextEmail;
    private Button buttonEditSave;

    private boolean isEditing = false; // Tracks the current mode (view/edit)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        // Enable the "up" button for navigation back to the previous screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Bind UI components
        textViewName = findViewById(R.id.text_view_name);
        textViewEmail = findViewById(R.id.text_view_email);
        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        buttonEditSave = findViewById(R.id.button_edit_save);

        // Load user profile data
        loadUserProfile();

        // Set up Edit/Save button
        buttonEditSave.setOnClickListener(v -> toggleEditMode());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close this activity and return to the previous one
        return true;
    }

    private void loadUserProfile() {
        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "N/A");
        String email = sharedPreferences.getString("email", "N/A");

        // Display the data
        textViewName.setText(name);
        textViewEmail.setText(email);

        // Set EditText values but keep them hidden initially
        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextName.setVisibility(View.GONE);
        editTextEmail.setVisibility(View.GONE);
    }

    private void toggleEditMode() {
        if (isEditing) {
            // Save the updated data
            String updatedName = editTextName.getText().toString().trim();
            String updatedEmail = editTextEmail.getText().toString().trim();

            if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", updatedName);
            editor.putString("email", updatedEmail);
            editor.apply();

            // Update the TextViews
            textViewName.setText(updatedName);
            textViewEmail.setText(updatedEmail);

            // Switch to View mode
            editTextName.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            textViewName.setVisibility(View.VISIBLE);
            textViewEmail.setVisibility(View.VISIBLE);
            buttonEditSave.setText("Edit");
            isEditing = false;

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        } else {
            // Switch to Edit mode
            textViewName.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            editTextName.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            buttonEditSave.setText("Save");
            isEditing = true;
        }
    }
}
