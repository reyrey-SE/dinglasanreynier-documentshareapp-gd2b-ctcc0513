package com.docsharing.documentshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchNotifications;
    private SeekBar fontSizeSeekBar;
    private Button buttonAccountSettings;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the toolbar with back navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Bind UI components
        switchNotifications = findViewById(R.id.switch_notifications);
        fontSizeSeekBar = findViewById(R.id.seekBar_font_size);
        buttonAccountSettings = findViewById(R.id.button_account_settings);

        // Load saved preferences
        loadPreferences();

        // Set up listeners
        switchNotifications.setOnCheckedChangeListener(this::onNotificationsSwitchChanged);
        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onFontSizeChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        buttonAccountSettings.setOnClickListener(v -> openAccountSettings());
    }

    private void loadPreferences() {
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", true);
        int fontSize = sharedPreferences.getInt("font_size", 14); // Default font size is 14

        switchNotifications.setChecked(notificationsEnabled);
        fontSizeSeekBar.setProgress(fontSize); // Set the saved font size on the SeekBar
    }

    private void onNotificationsSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications", isChecked);
        editor.apply();

        // Placeholder to enable/disable notifications
        if (isChecked) {
            enableNotifications();
        } else {
            disableNotifications();
        }
    }

    private void onFontSizeChanged(int fontSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("font_size", fontSize);
        editor.apply();

        // Placeholder for applying font size changes globally
        applyFontSize(fontSize);
    }

    private void openAccountSettings() {
        // Navigate to the AccountSettingsActivity (create this activity if needed)
        Intent intent = new Intent(this, AccountSettingsActivity.class);
        startActivity(intent);
    }

    private void enableNotifications() {
        // Implement logic to enable notifications
    }

    private void disableNotifications() {
        // Implement logic to disable notifications
    }

    private void applyFontSize(int fontSize) {
        // Implement logic to apply the font size across the app
    }

    // Handle the back navigation in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
