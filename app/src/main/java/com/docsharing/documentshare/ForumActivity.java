package com.docsharing.documentshare;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {

    private String username = "";
    private final List<Post> posts = new ArrayList<>();
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        // UI Elements
        EditText usernameInput = findViewById(R.id.username_input);
        Button setUsernameButton = findViewById(R.id.btn_set_username);
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        EditText postInput = findViewById(R.id.post_input);
        Button postButton = findViewById(R.id.btn_post);
        RecyclerView recyclerViewPosts = findViewById(R.id.recycler_view_posts);

        // Initialize RecyclerView
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(posts);
        recyclerViewPosts.setAdapter(postAdapter);

        // Set Username
        setUsernameButton.setOnClickListener(v -> {
            username = usernameInput.getText().toString().trim();
            if (!username.isEmpty()) {
                findViewById(R.id.username_layout).setVisibility(View.GONE);
                findViewById(R.id.posting_layout).setVisibility(View.VISIBLE);
                welcomeMessage.setText(String.format("Welcome, %s!", username));
            } else {
                Toast.makeText(this, "Please enter a username!", Toast.LENGTH_SHORT).show();
            }
        });

        postInput.setTextColor(Color.BLACK);
        postButton.setOnClickListener(v -> {
            postInput.setTextColor(Color.BLACK);
            String postText = postInput.getText().toString().trim();
            if (!postText.isEmpty()) {
                // Create a new post without comments
                posts.add(0, new Post(username, postText, 0));
                postAdapter.notifyItemInserted(0);
                postInput.setText("");
            } else {
                Toast.makeText(this, "Please write something to post!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
