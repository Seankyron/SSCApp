package com.example.sscapp.models;

public class QuickLink {
    private String title;
    private int iconResId;
    private String link;
    private boolean isMembershipCard;

    public QuickLink(String title, int iconResId, String link, boolean isMembershipCard) {
        this.title = title;
        this.iconResId = iconResId;
        this.link = link;
        this.isMembershipCard = isMembershipCard;
    }

    public String getTitle() { return title; }
    public int getIconResId() { return iconResId; }
    public String getLink() { return link; }
    public boolean isMembershipCard() { return isMembershipCard; }
}

