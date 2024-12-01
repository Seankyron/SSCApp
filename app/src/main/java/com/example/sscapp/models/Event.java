package com.example.sscapp.models;

import com.example.sscapp.R;

import java.util.Date;

public class Event {
    private final String title;
    private final String description;
    private final Date date;
    private final EventType eventType;
    private boolean completed;

    public Event(String title, String description, Date date, EventType eventType) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.eventType = eventType;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public EventType getEventType() {
        return eventType;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getIconResource() {
        // Return appropriate icon resource based on event type
        return eventType.getIconResource();
    }

    public enum EventType {
        ORIENTATION(R.drawable.add_shopping_cart),
        CELEBRATION(R.drawable.add_shopping_cart),
        ACADEMIC(R.drawable.add_shopping_cart),
        CULTURAL(R.drawable.add_shopping_cart),
        LEADERSHIP(R.drawable.add_shopping_cart),
        SOCIAL(R.drawable.add_shopping_cart),
        ENVIRONMENTAL(R.drawable.add_shopping_cart),
        ELECTION(R.drawable.add_shopping_cart),
        AWARDS(R.drawable.add_shopping_cart);

        private final int iconResource;

        EventType(int iconResource) {
            this.iconResource = iconResource;
        }

        public int getIconResource() {
            return iconResource;
        }
    }
}