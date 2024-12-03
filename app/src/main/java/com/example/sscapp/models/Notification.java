package com.example.sscapp.models;

public class Notification {
    private String title;
    private String description;
    private String timestamp;

    public Notification(String title, String description, String timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTimestamp() { return timestamp; }
}