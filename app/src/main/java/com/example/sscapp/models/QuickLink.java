package com.example.sscapp.models;

public class QuickLink {
    private String title;
    private int iconResId;
    private String link;

    public QuickLink(String title, int iconResId, String link) {
        this.title = title;
        this.iconResId = iconResId;
        this.link = link;
    }

    // Getters
    public String getTitle() { return title; }
    public int getIconResId() { return iconResId; }
    public String getLink() { return link; }
}

