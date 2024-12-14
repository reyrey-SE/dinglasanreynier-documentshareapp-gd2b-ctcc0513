package com.docsharing.documentshare;

public class Post {
    private final String username;
    private final String content;
    private int likes;
    private boolean isLiked;

    public Post(String username, String content, int likes) {
        this.username = username;
        this.content = content;
        this.likes = likes;
        this.isLiked = false;  // Default to not liked
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}

