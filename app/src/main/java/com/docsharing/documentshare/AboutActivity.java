package com.docsharing.documentshare;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About App");
        setSupportActionBar(toolbar);

        // Enable navigation back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // About info text
        TextView aboutText = findViewById(R.id.text_about);
        aboutText.setText(getString(R.string.app_name));

        // Set up button for contact or feedback
        findViewById(R.id.contact_button).setOnClickListener(v -> {
            // Handle contact functionality (e.g., sending an email)
            Toast.makeText(AboutActivity.this, "Contact us at support@docshare.com", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close the activity and return to the previous screen
        return true;
    }
}
