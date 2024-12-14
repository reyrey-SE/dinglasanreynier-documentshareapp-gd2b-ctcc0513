package com.docsharing.documentshare;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    private final Context context;
    private final List<Uri> fileUris;
    private final List<String> fileNames;
    private final List<Integer> filePrices;
    private final List<Integer> fileDownloadCounts;
    private final Set<Uri> purchasedFiles; // Track purchased files by Uri
    private final Set<Uri> favoriteFiles;  // Track favorite files by Uri

    public FileAdapter(Context context, List<Uri> fileUris, List<String> fileNames, List<Integer> filePrices, List<Integer> fileDownloadCounts) {
        this.context = context;
        this.fileUris = fileUris;
        this.fileNames = fileNames;
        this.filePrices = filePrices;
        this.fileDownloadCounts = fileDownloadCounts;
        this.purchasedFiles = new HashSet<>();
        this.favoriteFiles = new HashSet<>();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_item, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        Uri fileUri = fileUris.get(position);
        String fileName = fileNames.get(position);
        int price = filePrices.get(position);
        int downloadCount = fileDownloadCounts.get(position);

        holder.textFileName.setText(fileName);
        holder.textDownloadCount.setText("Downloads: " + downloadCount);
        holder.textFileSize.setText("Size: " + getFileSize(fileUri));

        // Set Buy Button state
        if (purchasedFiles.contains(fileUri)) {
            holder.buttonBuy.setText("Already Purchased");
            holder.buttonBuy.setEnabled(false);
            holder.buttonDownload.setEnabled(true); // Enable download for purchased files
        } else {
            holder.buttonBuy.setText(price > 0 ? "Buy (₱" + price + ")" : "Free");
            holder.buttonBuy.setEnabled(true);
            holder.buttonBuy.setOnClickListener(v -> {
                if (price > 0) {
                    initiatePayment(fileUri, price, position);
                } else {
                    downloadFile(fileUri, position);
                }
            });
            holder.buttonDownload.setEnabled(price == 0); // Only enable download for free files
        }

        // Set Download Button state
        holder.buttonDownload.setOnClickListener(v -> {
            if (price == 0 || purchasedFiles.contains(fileUri)) {
                downloadFile(fileUri, position);
            } else {
                Toast.makeText(context, "Please purchase the file to download.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Favorite Button state
        boolean isFavorite = favoriteFiles.contains(fileUri);
        holder.buttonFavorite.setText(isFavorite ? "Unfavorite" : "Favorite");
        holder.buttonFavorite.setOnClickListener(v -> toggleFavorite(holder, fileUri));
    }

    private void toggleFavorite(FileViewHolder holder, Uri fileUri) {
        boolean isFavorite = favoriteFiles.contains(fileUri);
        if (isFavorite) {
            favoriteFiles.remove(fileUri);
            holder.buttonFavorite.setText("Favorite");
            Toast.makeText(context, "Removed from favorites.", Toast.LENGTH_SHORT).show();
        } else {
            favoriteFiles.add(fileUri);
            holder.buttonFavorite.setText("Unfavorite");
            Toast.makeText(context, "Added to favorites.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initiatePayment(Uri fileUri, int price, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Payment for " + fileNames.get(position))
                .setMessage("Proceed to pay ₱" + price + " to download this file.")
                .setPositiveButton("Proceed", (dialog, which) -> {
                    if (mockPaymentProcess(price)) {
                        purchasedFiles.add(fileUri); // Track purchase by URI
                        notifyItemChanged(position);
                        Toast.makeText(context, "Payment Successful! You can now download the file.", Toast.LENGTH_SHORT).show();
                        downloadFile(fileUri, position);
                    } else {
                        Toast.makeText(context, "Payment Failed! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private boolean mockPaymentProcess(int price) {
        return price > 0; // Simulate successful payment
    }

    private void downloadFile(Uri fileUri, int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(fileUri);
        context.startActivity(intent);

        fileDownloadCounts.set(position, fileDownloadCounts.get(position) + 1);
        notifyItemChanged(position);
    }

    private String getFileSize(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            long fileSize = cursor.getLong(sizeIndex);
            cursor.close();
            return formatFileSize(fileSize);
        }
        return "Unknown";
    }

    private String formatFileSize(long sizeInBytes) {
        if (sizeInBytes >= 1024 * 1024) {
            return String.format("%.2f MB", sizeInBytes / (1024.0 * 1024));
        } else if (sizeInBytes >= 1024) {
            return String.format("%.2f KB", sizeInBytes / 1024.0);
        } else {
            return sizeInBytes + " B";
        }
    }

    @Override
    public int getItemCount() {
        return fileUris.size();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView textFileName;
        TextView textFileSize;
        TextView textDownloadCount;
        Button buttonBuy;
        Button buttonDownload;
        Button buttonFavorite;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            textFileName = itemView.findViewById(R.id.text_file_name);
            textFileSize = itemView.findViewById(R.id.text_file_size);
            textDownloadCount = itemView.findViewById(R.id.text_download_count);
            buttonBuy = itemView.findViewById(R.id.button_buy);
            buttonDownload = itemView.findViewById(R.id.button_download);
            buttonFavorite = itemView.findViewById(R.id.button_favorite);
        }
    }
}