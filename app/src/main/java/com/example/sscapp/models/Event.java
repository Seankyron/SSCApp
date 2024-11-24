package com.example.sscapp.models;

import com.example.sscapp.R;

import java.util.Date;

public class Event {
    private String title;
    private String description;
    private Date date;
    private EventType type;

    public enum EventType {
        ORIENTATION,    // ic_flight_takeoff
        CELEBRATION,    // ic_celebration
        ACADEMIC,       // ic_school
        CULTURAL,       // ic_stars
        LEADERSHIP,     // ic_trending_up
        SOCIAL,         // ic_favorite
        ENVIRONMENTAL,  // ic_eco
        ELECTION,       // ic_how_to_vote
        AWARDS,         // ic_emoji_events
        GENERAL        // ic_event
    }

    public Event(String title, String description, Date date, EventType type) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
    public EventType getType() { return type; }

    // Get icon resource based on event type
    public int getIconResource() {
        switch (type) {
            case ORIENTATION:
                return R.drawable.ic_flight_takeoff;
            case CELEBRATION:
                return R.drawable.ic_celebration;
            case ACADEMIC:
                return R.drawable.ic_school;
            case CULTURAL:
                return R.drawable.ic_stars;
            case LEADERSHIP:
                return R.drawable.ic_trending_up;
            case SOCIAL:
                return R.drawable.ic_favorite;
            case ENVIRONMENTAL:
                return R.drawable.ic_eco;
            case ELECTION:
                return R.drawable.ic_how_to_vote;
            case AWARDS:
                return R.drawable.ic_emoji_events;
            default:
                return R.drawable.ic_event;
        }
    }
}