package com.example.sscapp.models;

import java.util.HashMap;
import java.util.Map;

public class GroupedCartItem {
    private String name;
    private double price;
    private Map<String, Integer> sizes;
    private int imageResId;

    public GroupedCartItem(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.sizes = new HashMap<>();
    }

    public void addSize(String size, int quantity) {
        sizes.put(size, sizes.getOrDefault(size, 0) + quantity);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Integer> getSizes() {
        return sizes;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - ");
        for (Map.Entry<String, Integer> entry : sizes.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        sb.setLength(sb.length() - 2); // Remove last ", "
        return sb.toString();
    }
}