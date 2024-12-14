package com.docsharing.documentshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        // Set post details
        holder.username.setText(post.getUsername());
        holder.content.setText(post.getContent());
        holder.likes.setText(String.valueOf(post.getLikes()));

        // Update the like button based on whether the post is liked
        updateLikeButton(holder.likeButton, post.isLiked());

        // Handle the like button click event
        holder.likeButton.setOnClickListener(v -> {
            // Toggle like state
            if (post.isLiked()) {
                post.setLikes(post.getLikes() - 1); // Decrease like count
                post.setLiked(false); // Set the post as unliked
            } else {
                post.setLikes(post.getLikes() + 1); // Increase like count
                post.setLiked(true); // Set the post as liked
            }

            // Refresh the specific item view after updating the state
            notifyItemChanged(position);  // Notify adapter to update this particular item
        });
    }

    // Helper method to update the like button text
    private void updateLikeButton(Button likeButton, boolean isLiked) {
        if (isLiked) {
            likeButton.setText("Unlike");
        } else {
            likeButton.setText("Like");
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView username, content, likes;
        Button likeButton;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.post_username);
            content = itemView.findViewById(R.id.post_content);
            likes = itemView.findViewById(R.id.post_likes);
            likeButton = itemView.findViewById(R.id.btn_like);
        }
    }
}
