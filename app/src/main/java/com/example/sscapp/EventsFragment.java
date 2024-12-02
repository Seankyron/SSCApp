package com.example.sscapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.sscapp.adapters.EventAdapter;
import com.example.sscapp.models.Event;

public class EventsFragment extends Fragment {
    private static final String TAG = "EventsFragment";

    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter;
    private CalendarView calendarView;
    private TextView noEventsText;
    private Button toggleCalendarButton;
    private ConstraintLayout calendarContainer; // Updated class variable


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        initializeViews(view);

        // Setup components
        setupRecyclerView();
        setupCalendarView();
        setupToggleCalendarButton();

        // Initialize with today's events
        updateEventsForDate(new Date());
    }

    private void initializeViews(@NonNull View view) {
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        calendarView = view.findViewById(R.id.calendarView);
        noEventsText = view.findViewById(R.id.noEventsText);
        toggleCalendarButton = view.findViewById(R.id.toggleCalendarButton);
        calendarContainer = view.findViewById(R.id.calendarContainer); // Updated initializeViews method
    }

    private void setupRecyclerView() {
        if (getContext() == null) return;

        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter();
        eventsRecyclerView.setAdapter(eventAdapter);

        Log.d(TAG, "RecyclerView setup complete with " + eventAdapter.getItemCount() + " events");
    }

    private void setupCalendarView() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Log.d(TAG, "Date selected: " + year + "-" + (month + 1) + "-" + dayOfMonth);

            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);
            updateEventsForDate(selectedCalendar.getTime());
        });
    }

    private void setupToggleCalendarButton() {
        toggleCalendarButton.setOnClickListener(v -> toggleCalendarVisibility());
    }

    private void toggleCalendarVisibility() {
        if (calendarContainer.getVisibility() == View.GONE) { // Updated toggleCalendarVisibility method
            calendarContainer.setVisibility(View.VISIBLE);
            toggleCalendarButton.setText("HIDE CALENDAR");
        } else {
            calendarContainer.setVisibility(View.GONE);
            toggleCalendarButton.setText("SHOW CALENDAR");
        }
    }

    private void updateEventsForDate(Date date) {
        // Normalize the date to remove time components
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date normalizedDate = calendar.getTime();

        // Format the date for logging
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDateStr = dateFormat.format(normalizedDate);
        Log.d(TAG, "Updating events for date: " + selectedDateStr);

        // Fetch and display events
        List<Event> eventsForDate = eventAdapter.getEventsForDate(normalizedDate);
        Log.d(TAG, "Total events in adapter: " + eventAdapter.getItemCount());
        Log.d(TAG, "Events found for " + selectedDateStr + ": " + eventsForDate.size());

        // Update adapter and handle empty state
        eventAdapter.setEvents(eventsForDate);

        if (eventsForDate.isEmpty()) { // Updated updateEventsForDate method
            eventsRecyclerView.setVisibility(View.GONE);
            noEventsText.setVisibility(View.VISIBLE);
        } else {
            eventsRecyclerView.setVisibility(View.VISIBLE);
            noEventsText.setVisibility(View.GONE);
        }
    }
}

