package com.example.sscapp;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sscapp.adapters.UpdatesAdapter;
import com.example.sscapp.models.Announcement;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UpdatesFragment extends Fragment {

    private RecyclerView pinnedRecyclerView;
    private RecyclerView recentRecyclerView;
    private UpdatesAdapter pinnedAdapter;
    private UpdatesAdapter recentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updates, container, false);

        pinnedRecyclerView = view.findViewById(R.id.pinnedUpdatesRecyclerView);
        recentRecyclerView = view.findViewById(R.id.recentUpdatesRecyclerView);

        pinnedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Announcement> allAnnouncements = getAnnouncements();
        List<Announcement> pinnedAnnouncements = new ArrayList<>();
        List<Announcement> recentAnnouncements = new ArrayList<>();

        for (Announcement announcement : allAnnouncements) {
            if (announcement.isPinnedAttachment()) {
                pinnedAnnouncements.add(announcement);
            } else {
                recentAnnouncements.add(announcement);
            }
        }

        pinnedAdapter = new UpdatesAdapter(pinnedAnnouncements);
        recentAdapter = new UpdatesAdapter(recentAnnouncements);

        pinnedRecyclerView.setAdapter(pinnedAdapter);
        recentRecyclerView.setAdapter(recentAdapter);

        return view;
    }

    List<Announcement> getAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement(1, "University Week Celebration", "Join us for a week-long celebration filled with competitions, performances, and more!", "Important", "Event Updates", "Student Affairs Office", "https://example.com/university-week.jpg", true, "2023-05-15"));
        announcements.add(new Announcement(2, "Dean's Lister Recognition Day", "Congratulations to all students who made it to the Dean's List!", "Important", "Academic Achievements", "Office of the Dean", "https://example.com/deans-list.jpg", false, "2023-05-20"));
        announcements.add(new Announcement(3, "Class Suspension Advisory", "All classes are suspended today due to inclement weather.", "Urgent", "Weather Advisory", "Office of the President", "https://example.com/weather-advisory.jpg", true, "2023-05-18"));
        announcements.add(new Announcement(4, "New Laboratory Equipment", "College of Engineering receives new state-of-the-art equipment", "Normal", "Campus News", "College of Engineering", "https://example.com/new-equipment.jpg", false, "2023-05-22"));
        return announcements;
    }

    public static List<Announcement> getStaticAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement(1, "University Week Celebration", "Join us for a week-long celebration filled with competitions, performances, and more!", "Important", "Event Updates", "Student Affairs Office", "https://example.com/university-week.jpg", true, "2023-05-15"));
        announcements.add(new Announcement(2, "Dean's Lister Recognition Day", "Congratulations to all students who made it to the Dean's List!", "Important", "Academic Achievements", "Office of the Dean", "https://example.com/deans-list.jpg", false, "2023-05-20"));
        announcements.add(new Announcement(3, "Class Suspension Advisory", "All classes are suspended today due to inclement weather.", "Urgent", "Weather Advisory", "Office of the President", "https://example.com/weather-advisory.jpg", true, "2023-05-18"));
        announcements.add(new Announcement(4, "New Laboratory Equipment", "College of Engineering receives new state-of-the-art equipment", "Normal", "Campus News", "College of Engineering", "https://example.com/new-equipment.jpg", false, "2023-05-22"));
        return announcements;
    }
}
