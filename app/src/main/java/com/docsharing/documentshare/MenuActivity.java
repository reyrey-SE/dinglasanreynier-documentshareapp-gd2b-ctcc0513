package com.docsharing.documentshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));
        setSupportActionBar(toolbar);

        // Enable Up button (back navigation)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onIncomeClick(View view) {
        Toast.makeText(this, "Income Feature available soon", Toast.LENGTH_SHORT).show();
    }

    public void onWithdrawClick(View view) {
        Toast.makeText(this, "Withdraw Feature available soon", Toast.LENGTH_SHORT).show();
    }

    public void onDownloadHistoryClick(View view) {
        // Display a Toast message instead of navigating to another activity
        Toast.makeText(this, "Download File History available soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        } else if (id == R.id.action_about) {
            showAboutDialog();
            return true;
        } else if (id == R.id.action_feedback) {
            startFeedbackActivity();
            return true;
        } else if (id == R.id.action_tips) {
            startActivity(new Intent(this, TipsActivity.class));
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (id == R.id.action_exit) {
            finish();
            return true;
        } else if (id == android.R.id.home) {
            // Go back to the previous activity (DashboardActivity) without restarting it
            onBackPressed();  // This method calls finish() and restores the previous activity
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("Version 1.0\nDeveloped by Reynier Dinglasan\nPrivacy Policy: [Link]")
                .setPositiveButton("OK", null)
                .show();
    }

    private void startFeedbackActivity() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "reynierdinglasan07@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Document Share App");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
