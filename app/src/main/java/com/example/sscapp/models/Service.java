package com.example.sscapp.models;

public class Service {
    private int id;
    private String title;
    private String description;
    private int iconResId;
    private String status;

    public Service(int id, String title, String description, int iconResId, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconResId = iconResId;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getIconResId() { return iconResId; }
    public String getStatus() { return status; }
}

