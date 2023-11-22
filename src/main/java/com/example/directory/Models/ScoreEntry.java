package com.example.directory.Models;

public class ScoreEntry {
    private String username;
    private int score;

    public ScoreEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
