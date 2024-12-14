package com.docsharing.documentshare;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {

    private final List<String> allTips = new ArrayList<>();
    private final List<String> displayedTips = new ArrayList<>();
    private TipsAdapter tipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("App Tips");
        setSupportActionBar(toolbar);

        // Enable navigation back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize tips
        initializeTips();

        // Set up RecyclerView for displaying tips
        RecyclerView tipsRecyclerView = findViewById(R.id.recycler_view_tips);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tipsAdapter = new TipsAdapter(this, displayedTips);
        tipsRecyclerView.setAdapter(tipsAdapter);

        // Search bar functionality
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTips(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close the activity and return to the previous screen
        return true;
    }

    private void initializeTips() {
        allTips.add("Upload files by clicking the 'Upload' button to expand your file library.");
        allTips.add("Use the 'Sell' button to set prices and sell your documents.");
        allTips.add("Use the search bar to quickly find files in your library.");
        allTips.add("Sort files by paid, free, or most downloaded using the 'Sort' button.");
        allTips.add("Check download counts to identify your most popular files.");
        allTips.add("Give your files clear, descriptive names to make them easy to recognize.");
        allTips.add("Review and update file prices regularly to stay competitive.");
        allTips.add("Organize your files by sorting to keep your dashboard tidy.");
        allTips.add("Ensure your files are correctly uploaded by previewing them.");
        allTips.add("Regularly interact with the 'Forum' feature to stay updated.");

        displayedTips.addAll(allTips);
    }

    private void filterTips(String query) {
        displayedTips.clear();

        if (query.isEmpty()) {
            displayedTips.addAll(allTips);
        } else {
            for (String tip : allTips) {
                if (tip.toLowerCase().contains(query.toLowerCase())) {
                    displayedTips.add(tip);
                }
            }
        }

        tipsAdapter.notifyDataSetChanged();
    }
}
