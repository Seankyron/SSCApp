package com.example.sscapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.sscapp.adapters.EventAdapter;
import com.example.sscapp.models.Event;

public class EventsFragment extends Fragment {
    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter;
    private CalendarView calendarView;
    private TextView noEventsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        calendarView = view.findViewById(R.id.calendarView);

        setupRecyclerView();
        setupCalendarView();

        // Initialize with today's events
        updateEventsForDate(new Date());

        return view;
    }

    private void setupRecyclerView() {
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter();
        eventsRecyclerView.setAdapter(eventAdapter);
    }

    private void setupCalendarView() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);
            updateEventsForDate(selectedCalendar.getTime());
        });
    }

    private void updateEventsForDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDateStr = dateFormat.format(date);
        Log.d("SelectedDate", "Selected date: " + selectedDateStr);

        List<Event> eventsForDate = eventAdapter.getEventsForDate(date);

        eventAdapter.setEvents(eventsForDate);

        Log.d("SelectedDate", "Events count: " + eventsForDate.size());

        if (eventsForDate.isEmpty()) {
            eventsRecyclerView.setVisibility(View.GONE);
        } else {
            eventsRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
