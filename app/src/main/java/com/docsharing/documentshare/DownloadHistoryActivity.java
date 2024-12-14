package com.docsharing.documentshare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_history);

        // Load and display the download history
        loadDownloadHistory();
    }

    private void loadDownloadHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("download_history", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        List<String> downloadHistoryList = new ArrayList<>();
        // Iterate over the saved entries and format them for display
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String fileName = entry.getKey();
            String downloadDetails = (String) entry.getValue();
            String historyItem = fileName + " - " + downloadDetails; // Format as needed
            downloadHistoryList.add(historyItem);
            Log.d("DownloadHistory", "File: " + fileName + ", Details: " + downloadDetails);
        }

        // Set the adapter to display the download history in the ListView
        ListView listView = findViewById(R.id.listViewDownloadHistory);
        DownloadHistoryAdapter adapter = new DownloadHistoryAdapter(this, downloadHistoryList);
        listView.setAdapter(adapter);
    }
}
