package com.example.sscapp.models;
public class Announcement {
    private int id;
    private String title;
    private String description;
    private String type;
    private String category;
    private String author;
    private String image;
    private boolean isPinnedAttachment;
    private String date;

    public Announcement(int id, String title, String description, String type, String category, String author, String image, boolean isPinnedAttachment, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.author = author;
        this.image = image;
        this.isPinnedAttachment = isPinnedAttachment;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public boolean isPinnedAttachment() {
        return isPinnedAttachment;
    }

    public String getDate() {
        return date;
    }

    public void setPinnedAttachment(boolean isPinnedAttachment) {
        this.isPinnedAttachment = isPinnedAttachment;
    }

    public void togglePinned() {
        this.isPinnedAttachment = !this.isPinnedAttachment;
    }
}