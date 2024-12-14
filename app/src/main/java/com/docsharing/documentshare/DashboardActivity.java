package com.docsharing.documentshare;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_FILE = 100;
    private static final int REQUEST_CODE_SELL_FILE = 101;

    private final List<Uri> fileUris = new ArrayList<>();
    private final List<String> fileNames = new ArrayList<>();
    private final List<Integer> filePrices = new ArrayList<>();
    private final List<Integer> fileDownloadCounts = new ArrayList<>();
    private FileAdapter fileAdapter;

    private int currentFilePrice = 0;

    private List<String> allFileNames = new ArrayList<>();
    private List<Uri> allFileUris = new ArrayList<>();
    private List<Integer> allFilePrices = new ArrayList<>();
    private List<Integer> allFileDownloadCounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons and RecyclerView
        findViewById(R.id.button_upload).setOnClickListener(v -> openFilePicker(REQUEST_CODE_SELECT_FILE));
        findViewById(R.id.button_sell).setOnClickListener(v -> showSetPriceDialog());
        findViewById(R.id.button_sort).setOnClickListener(v -> showSortDialog());
        findViewById(R.id.com_forum).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ForumActivity.class);
            startActivity(intent);
        });

        // Menu button functionality
        findViewById(R.id.menu_button).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_files);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter(this, fileUris, fileNames, filePrices, fileDownloadCounts);
        recyclerView.setAdapter(fileAdapter);

        // Search bar functionality
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFiles(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void openFilePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            String fileName = getFileName(fileUri);

            if (fileUri != null && fileName != null) {
                fileUris.add(fileUri);
                fileNames.add(fileName);

                if (requestCode == REQUEST_CODE_SELECT_FILE) {
                    filePrices.add(0);
                } else if (requestCode == REQUEST_CODE_SELL_FILE) {
                    filePrices.add(currentFilePrice);
                }

                fileDownloadCounts.add(0);
                fileAdapter.notifyDataSetChanged();

                allFileUris.add(fileUri);
                allFileNames.add(fileName);
                allFilePrices.add(filePrices.get(filePrices.size() - 1));
                allFileDownloadCounts.add(fileDownloadCounts.get(fileDownloadCounts.size() - 1));

                Toast.makeText(this, "File added: " + fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileName(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String fileName = cursor.getString(nameIndex);
            cursor.close();
            return fileName;
        }
        return null;
    }

    private void filterFiles(String query) {
        fileUris.clear();
        fileNames.clear();
        filePrices.clear();
        fileDownloadCounts.clear();

        if (query.isEmpty()) {
            fileUris.addAll(allFileUris);
            fileNames.addAll(allFileNames);
            filePrices.addAll(allFilePrices);
            fileDownloadCounts.addAll(allFileDownloadCounts);
        } else {
            for (int i = 0; i < allFileNames.size(); i++) {
                if (allFileNames.get(i).toLowerCase().contains(query.toLowerCase())) {
                    fileUris.add(allFileUris.get(i));
                    fileNames.add(allFileNames.get(i));
                    filePrices.add(allFilePrices.get(i));
                    fileDownloadCounts.add(allFileDownloadCounts.get(i));
                }
            }
        }

        fileAdapter.notifyDataSetChanged();
    }

    private void showSetPriceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Price for File");

        EditText inputPrice = new EditText(this);
        builder.setView(inputPrice);

        builder.setPositiveButton("Set Price", (dialog, which) -> {
            String priceText = inputPrice.getText().toString();
            currentFilePrice = priceText.isEmpty() ? 0 : Integer.parseInt(priceText);
            openFilePicker(REQUEST_CODE_SELL_FILE);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void showSortDialog() {
        String[] options = {"Paid Files", "Free Files", "Most Downloaded"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort Files");
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    sortFilesByPaid();
                    break;
                case 1:
                    sortFilesByFree();
                    break;
                case 2:
                    sortFilesByDownloadCount();
                    break;
            }
        });
        builder.show();
    }

    private void sortFilesByPaid() {
        sortFiles((i1, i2) -> Integer.compare(filePrices.get(i2), filePrices.get(i1)));
        Toast.makeText(this, "Sorted by Paid Files", Toast.LENGTH_SHORT).show();
    }

    private void sortFilesByFree() {
        sortFiles((i1, i2) -> Integer.compare(filePrices.get(i1), filePrices.get(i2)));
        Toast.makeText(this, "Sorted by Free Files", Toast.LENGTH_SHORT).show();
    }

    private void sortFilesByDownloadCount() {
        sortFiles((i1, i2) -> Integer.compare(fileDownloadCounts.get(i2), fileDownloadCounts.get(i1)));
        Toast.makeText(this, "Sorted by Most Downloaded Files", Toast.LENGTH_SHORT).show();
    }

    private void sortFiles(java.util.Comparator<Integer> comparator) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fileUris.size(); i++) {
            indices.add(i);
        }

        indices.sort(comparator);

        List<Uri> sortedUris = new ArrayList<>();
        List<String> sortedNames = new ArrayList<>();
        List<Integer> sortedPrices = new ArrayList<>();
        List<Integer> sortedDownloads = new ArrayList<>();

        for (int index : indices) {
            sortedUris.add(fileUris.get(index));
            sortedNames.add(fileNames.get(index));
            sortedPrices.add(filePrices.get(index));
            sortedDownloads.add(fileDownloadCounts.get(index));
        }

        fileUris.clear();
        fileNames.clear();
        filePrices.clear();
        fileDownloadCounts.clear();

        fileUris.addAll(sortedUris);
        fileNames.addAll(sortedNames);
        filePrices.addAll(sortedPrices);
        fileDownloadCounts.addAll(sortedDownloads);

        fileAdapter.notifyDataSetChanged();
    }
}