package com.example.sscapp.models;

import com.example.sscapp.R;

import java.util.Date;

public class Event {
    private String title;
    private String description;
    private Date date;
    private EventType eventType;
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
        ORIENTATION(R.drawable.ic_flight_takeoff),
        CELEBRATION(R.drawable.ic_celebration),
        ACADEMIC(R.drawable.ic_school),
        CULTURAL(R.drawable.ic_stars),
        LEADERSHIP(R.drawable.ic_trending_up),
        SOCIAL(R.drawable.ic_favorite),
        ENVIRONMENTAL(R.drawable.ic_eco),
        ELECTION(R.drawable.ic_how_to_vote),
        AWARDS(R.drawable.ic_emoji_events);

        private final int iconResource;

        EventType(int iconResource) {
            this.iconResource = iconResource;
        }

        public int getIconResource() {
            return iconResource;
        }
    }
}