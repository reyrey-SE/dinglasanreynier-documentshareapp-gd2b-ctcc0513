package com.docsharing.documentshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DownloadHistoryAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> downloadHistoryList;

    public DownloadHistoryAdapter(Context context, List<String> downloadHistoryList) {
        super(context, 0, downloadHistoryList);
        this.context = context;
        this.downloadHistoryList = downloadHistoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.download_history_item, parent, false);
        }

        TextView fileNameTextView = convertView.findViewById(R.id.text_file_name);
        TextView downloadDetailsTextView = convertView.findViewById(R.id.text_download_details);

        String historyItem = downloadHistoryList.get(position);
        String[] details = historyItem.split(" - "); //  "FileName - Details" format
        fileNameTextView.setText(details[0]);
        downloadDetailsTextView.setText(details[1]);

        return convertView;
    }
}
