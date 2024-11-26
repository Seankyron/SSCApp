package com.example.sscapp.quickaccesscard;

import android.os.Bundle;
import android.graphics.Rect;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.example.sscapp.R;
import com.example.sscapp.adapters.EventAdapter;
import com.example.sscapp.models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventTimelineActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_timeline);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Events Timeline");
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.eventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Event> events = getEvents();
        adapter = new EventAdapter(events);
        recyclerView.setAdapter(adapter);

    }

    private List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // Add events with appropriate event types
        calendar.set(2024, Calendar.AUGUST, 15);
        events.add(new Event("Take Off V.4.0",
                "First Year Student Orientation - Welcome to the start of your academic journey! Join us for an exciting introduction to university life, meet your peers, and learn about campus resources.",
                calendar.getTime(), Event.EventType.ORIENTATION));

        calendar.set(2024, Calendar.SEPTEMBER, 30);
        events.add(new Event("Froshie Fair V.6.0",
                "A vibrant welcome celebration for our freshmen students. Experience campus culture, join student organizations, and create lasting friendships.",
                calendar.getTime(), Event.EventType.CELEBRATION));

        calendar.set(2024, Calendar.OCTOBER, 4);
        events.add(new Event("World Teacher's Day",
                "Join us in honoring our dedicated educators who shape the future through their commitment to excellence in education.",
                calendar.getTime(), Event.EventType.ACADEMIC));

        calendar.set(2024, Calendar.OCTOBER, 15);
        events.add(new Event("Alangilan 40th and Mr. and Ms. BatStateU Alangilan",
                "A grand celebration of our campus's 40th anniversary combined with our annual pageant showcasing talent and excellence.",
                calendar.getTime(), Event.EventType.CELEBRATION));

        calendar.set(2024, Calendar.NOVEMBER, 15);
        events.add(new Event("SinagTaLa: 12th Parol Making Contest",
                "Experience Filipino culture and creativity through our sustainable parol-making competition. Witness innovation meets tradition!",
                calendar.getTime(), Event.EventType.CULTURAL));

        calendar.set(2025, Calendar.JANUARY, 15);
        events.add(new Event("EMERGE V.8.0",
                "Our flagship leadership development program designed to empower future leaders with essential skills and knowledge.",
                calendar.getTime(), Event.EventType.LEADERSHIP));

        calendar.set(2025, Calendar.FEBRUARY, 14);
        events.add(new Event("Area 143",
                "Celebrate love and friendship in this special Valentine's Day event filled with music, games, and heartwarming activities.",
                calendar.getTime(), Event.EventType.SOCIAL));

        calendar.set(2025, Calendar.MARCH, 15);
        events.add(new Event("palaSSCpan V.7.0",
                "Join us for an exciting celebration of World Engineering Day featuring exhibitions, competitions, and innovative showcases.",
                calendar.getTime(), Event.EventType.ACADEMIC));

        calendar.set(2025, Calendar.APRIL, 22);
        events.add(new Event("Eco Summit V.7.0",
                "Be part of our Earth Day environmental awareness campaign. Learn about sustainability and take action for our planet.",
                calendar.getTime(), Event.EventType.ENVIRONMENTAL));

        calendar.set(2025, Calendar.MAY, 15);
        events.add(new Event("elecSSCion 2025",
                "Exercise your right to vote and shape the future of student leadership. Your voice matters in choosing the next student council.",
                calendar.getTime(), Event.EventType.ELECTION));

        calendar.set(2025, Calendar.JUNE, 15);
        events.add(new Event("2nd Annual SINAG Awards",
                "A prestigious ceremony recognizing outstanding achievements in academics, leadership, and community service.",
                calendar.getTime(), Event.EventType.AWARDS));

        return events;
    }

    private static class TimelineItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                outRect.top = 24;
            }
            if (position == parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = 24;
            }
        }
    }
}