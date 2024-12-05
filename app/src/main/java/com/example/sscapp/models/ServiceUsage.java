package com.example.sscapp.models;

public class ServiceUsage {
    private String name;
    private int usageCount;
    private int iconResourceId;

    public ServiceUsage(String name, int usageCount, int iconResourceId) {
        this.name = name;
        this.usageCount = usageCount;
        this.iconResourceId = iconResourceId;
    }

    // Getters
    public String getName() { return name; }
    public int getUsageCount() { return usageCount; }
    public int getIconResourceId() { return iconResourceId; }
}