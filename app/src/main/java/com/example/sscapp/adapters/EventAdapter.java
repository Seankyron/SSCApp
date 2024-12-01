package com.example.sscapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.sscapp.R;
import com.example.sscapp.models.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events = new ArrayList<>();
    private Date currentDate = new Date();

    public EventAdapter() {
        this.events = getEvents();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        Log.d("EventAdapter", "Setting events: " + events.size());
        notifyDataSetChanged();
    }

    public List<Event> getEvents() {
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

    public List<Event> getEventsForDate(Date date) {
        List<Event> eventsForDate = new ArrayList<>();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(date);
        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
        selectedCalendar.set(Calendar.MINUTE, 0);
        selectedCalendar.set(Calendar.SECOND, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);

        for (Event event : events) {
            Calendar eventCalendar = Calendar.getInstance();
            eventCalendar.setTime(event.getDate());
            eventCalendar.set(Calendar.HOUR_OF_DAY, 0);
            eventCalendar.set(Calendar.MINUTE, 0);
            eventCalendar.set(Calendar.SECOND, 0);
            eventCalendar.set(Calendar.MILLISECOND, 0);

            if (selectedCalendar.getTimeInMillis() == eventCalendar.getTimeInMillis()) {
                eventsForDate.add(event);
            }
        }

        return eventsForDate;
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView dateTextView;
        private ImageView eventImage;
        private TextView eventType;
        private TextView eventCategory;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.eventTitle);
            descriptionTextView = itemView.findViewById(R.id.eventDescription);
            dateTextView = itemView.findViewById(R.id.eventDate);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventType = itemView.findViewById(R.id.eventType);
            eventCategory = itemView.findViewById(R.id.eventCategory);
        }

        void bind(Event event) {
            titleTextView.setText(event.getTitle());
            descriptionTextView.setText(event.getDescription());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            dateTextView.setText(dateFormat.format(event.getDate()));

            eventImage.setImageResource(event.getIconResource());
            eventType.setText(event.getEventType().name());
            eventCategory.setText(event.getEventType().name());
        }
    }
}
