package com.example.sscapp.adapters;

import com.example.sscapp.models.Announcement;

public interface OnAnnouncementActionListener {
    void onPinClicked(Announcement announcement, int position);
    void onDeleteClicked(Announcement announcement, int position);
}
