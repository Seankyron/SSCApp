package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sscapp.R;
import com.example.sscapp.models.Event;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AdminEventFragment extends Fragment {

    private CalendarView calendarView;
    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private AutoCompleteTextView eventTypeSpinner;
    private Date selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_events, container, false);

        calendarView = view.findViewById(R.id.eventCalendarView);
        titleEditText = view.findViewById(R.id.purposeEditText);
        descriptionEditText = view.findViewById(R.id.eventDescriptionEditText);
        eventTypeSpinner = view.findViewById(R.id.timeSlotSpinner);

        setupCalendarView();
        setupEventTypeSpinner();

        return view;
    }

    private void setupCalendarView() {
        selectedDate = new Date(); // Default to current date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            selectedDate = calendar.getTime();
        });
    }

    private void setupEventTypeSpinner() {
        ArrayAdapter<Event.EventType> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                Event.EventType.values()
        );
        eventTypeSpinner.setAdapter(adapter);
    }

    public Event createEventFromInput() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        Event.EventType eventType = (Event.EventType) eventTypeSpinner.getTag();

        return new Event(title, description, selectedDate, eventType);
    }
}