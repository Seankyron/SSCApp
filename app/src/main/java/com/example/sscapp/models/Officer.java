package com.example.sscapp.models;


public class Officer {
    private String name;
    private String position;
    private String details;

    public Officer(String name, String position, String details) {
        this.name = name;
        this.position = position;
        this.details = details;
    }

    // Getters
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getDetails() { return details; }
}