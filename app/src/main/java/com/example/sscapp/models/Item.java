package com.example.sscapp.models;

public class Item {
    private String name;
    private String location;
    private String date;
    private int imageResource;
    private String description;
    private String contactInfo;

    public Item(String name, String location, String date, int imageResource) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.imageResource = imageResource;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}