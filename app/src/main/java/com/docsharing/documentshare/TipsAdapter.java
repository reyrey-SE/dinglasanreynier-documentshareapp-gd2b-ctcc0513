package com.docsharing.documentshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {

    private final Context context;
    private final List<String> tips;

    public TipsAdapter(Context context, List<String> tips) {
        this.context = context;
        this.tips = tips;
    }

    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tip, parent, false);
        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        holder.tipTextView.setText(tips.get(position));
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    static class TipsViewHolder extends RecyclerView.ViewHolder {
        TextView tipTextView;

        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);
            tipTextView = itemView.findViewById(R.id.text_view_tip);
        }
    }
}
