package com.docsharing.documentshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {

    private final Context context;
    private final List<String> questions;
    private final List<String> answers;

    public FAQAdapter(Context context, List<String> questions, List<String> answers) {
        this.context = context;
        this.questions = questions;
        this.answers = answers;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        holder.questionTextView.setText(questions.get(position));
        holder.answerTextView.setText(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class FAQViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.text_view_question);
            answerTextView = itemView.findViewById(R.id.text_view_answer);
        }
    }
}
