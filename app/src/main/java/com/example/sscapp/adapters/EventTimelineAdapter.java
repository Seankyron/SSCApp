package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.models.Event;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventTimelineAdapter extends RecyclerView.Adapter<EventTimelineAdapter.EventViewHolder> {
    private final List<Event> events;
    private final SimpleDateFormat dateFormat;

    public interface EventCompletionListener {
        void onEventCompletionChanged(boolean isCompleted);
    }

    public EventTimelineAdapter(List<Event> events) {
        this.events = events;
        this.dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_timeline, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);

        holder.eventTitle.setText(event.getTitle());
        holder.eventDate.setText(dateFormat.format(event.getDate()));
        holder.eventDescription.setText(event.getDescription());
        holder.eventTypeIcon.setImageResource(event.getIconResource());


        // Set up description expansion/collapse
        holder.expandCollapseButton.setOnClickListener(v -> {
            boolean isExpanded = holder.eventDescription.getMaxLines() > 2;
            holder.eventDescription.setMaxLines(isExpanded ? 2 : Integer.MAX_VALUE);
            holder.expandCollapseButton.setText(isExpanded ? "Show more" : "Show less");
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        final TextView eventTitle;
        final TextView eventDate;
        final TextView eventDescription;
        final TextView expandCollapseButton;
        final ImageView eventTypeIcon;


        EventViewHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            expandCollapseButton = itemView.findViewById(R.id.expandCollapseButton);
            eventTypeIcon = itemView.findViewById(R.id.eventTypeIcon);
        }
    }
}