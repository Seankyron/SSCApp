package com.example.sscapp.models;

public class Announcement {
    private int id;
    private String title;
    private String description;
    private String type;
    private String category;
    private String author;
    private String image;

    public Announcement(int id, String title, String description, String type, String category, String author, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.author = author;
        this.image = image;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getAuthor() { return author; }
    public String getImage() { return image; }
}

