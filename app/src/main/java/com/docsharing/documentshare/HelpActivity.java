package com.docsharing.documentshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private final List<String> faqQuestions = new ArrayList<>();
    private final List<String> faqAnswers = new ArrayList<>();
    private final List<String> displayedQuestions = new ArrayList<>();
    private final List<String> displayedAnswers = new ArrayList<>();
    private FAQAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Help & Support");
        setSupportActionBar(toolbar);

        // Enable navigation back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize FAQ data
        initializeFAQs();

        // Set up RecyclerView
        RecyclerView faqRecyclerView = findViewById(R.id.recycler_view_faq);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        faqAdapter = new FAQAdapter(this, displayedQuestions, displayedAnswers);
        faqRecyclerView.setAdapter(faqAdapter);

        // Search bar functionality
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFAQs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Contact support button
        findViewById(R.id.button_contact_support).setOnClickListener(v -> openContactSupportDialog());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close the activity and return to the previous screen
        return true;
    }

    private void initializeFAQs() {
        // Sample FAQ data
        faqQuestions.add("How to upload files?");
        faqAnswers.add("To upload files, go to the Dashboard and click on the 'Upload' button.");

        faqQuestions.add("How to sell files?");
        faqAnswers.add("To sell files, go to the Dashboard, set a price for the file, and upload it as a sellable file.");

        faqQuestions.add("How to use the search bar?");
        faqAnswers.add("Use the search bar at the top of the Dashboard to find files by typing keywords.");

        faqQuestions.add("How to sort files?");
        faqAnswers.add("Click on the 'Sort' button in the Dashboard to organize files by Paid, Free, or Most Downloaded.");

        faqQuestions.add("How to check the popularity of a file?");
        faqAnswers.add("Each file in the Dashboard shows a download count to indicate its popularity.");

        faqQuestions.add("What file types can I upload?");
        faqAnswers.add("You can upload any file type supported by your device's file picker.");

        faqQuestions.add("How to contact support?");
        faqAnswers.add("Click on the 'Contact Support' button on the Help page to send us an email or call.");

        faqQuestions.add("How to navigate to the Forum?");
        faqAnswers.add("Click on the 'Forum' button on the Dashboard to participate in discussions.");

        faqQuestions.add("Can I preview my uploaded files?");
        faqAnswers.add("Yes, you can click on a file in the Dashboard to preview it before sharing or selling.");

        // Initially display all FAQs
        displayedQuestions.addAll(faqQuestions);
        displayedAnswers.addAll(faqAnswers);
    }


    private void filterFAQs(String query) {
        displayedQuestions.clear();
        displayedAnswers.clear();

        if (query.isEmpty()) {
            displayedQuestions.addAll(faqQuestions);
            displayedAnswers.addAll(faqAnswers);
        } else {
            for (int i = 0; i < faqQuestions.size(); i++) {
                if (faqQuestions.get(i).toLowerCase().contains(query.toLowerCase())) {
                    displayedQuestions.add(faqQuestions.get(i));
                    displayedAnswers.add(faqAnswers.get(i));
                }
            }
        }

        faqAdapter.notifyDataSetChanged();
    }

    private void openContactSupportDialog() {
        String[] options = {"Email Support", "Call Support"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contact Support");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                // Email support
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:support@docsharing.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
                try {
                    startActivity(emailIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
                }
            } else if (which == 1) {
                // Call support
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+1234567890"));
                try {
                    startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "No dialer app found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }
}
