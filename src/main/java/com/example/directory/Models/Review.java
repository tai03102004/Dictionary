package com.example.directory.Models;

public class Review implements Reviewable {
    private double start;
    private String title;
    private String comment;

    public Review(double start, String title, String comment) {
        this.start = start;
        this.title = title;
        this.comment = comment;
    }

    // Constructors, getters, setters...

    @Override
    public double getStart() {
        return start;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getComment() {
        return comment;
    }
}