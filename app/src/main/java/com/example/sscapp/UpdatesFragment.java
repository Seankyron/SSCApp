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

public class UpdatesFragment extends Fragment {
    private RecyclerView recyclerView;
    private UpdatesAdapter adapter;
    private List<Announcement> announcements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updates, container, false);

        recyclerView = view.findViewById(R.id.announcementsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        announcements = getAnnouncements(); // Method to get announcements
        adapter = new UpdatesAdapter(announcements);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Announcement> getAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement(1, "University Week Celebration", "Join us for a week-long celebration filled with competitions, performances, and more!", "Important", "Event Updates", "Student Affairs Office", "https://example.com/university-week.jpg"));
        announcements.add(new Announcement(2, "Dean's Lister Recognition Day", "Congratulations to all students who made it to the Dean's List!", "Important", "Academic Achievements", "Office of the Dean", "https://example.com/deans-list.jpg"));
        announcements.add(new Announcement(3, "Class Suspension Advisory", "All classes are suspended today due to inclement weather.", "Urgent", "Weather Advisory", "Office of the President", "https://example.com/weather-advisory.jpg"));
        announcements.add(new Announcement(4, "New Laboratory Equipment", "College of Engineering receives new state-of-the-art equipment", "Normal", "Campus News", "College of Engineering", "https://example.com/new-equipment.jpg"));
        return announcements;
    }
}

