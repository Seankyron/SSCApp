package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.adapters.NotificationAdapter;
import com.example.sscapp.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.notifications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create dummy notifications for testing
        List<Notification> notifications = createDummyNotifications();
        adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Notification> createDummyNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(
                "Store Update",
                "New items added to the student store",
                "2 hours ago"
        ));
        notifications.add(new Notification(
                "Event Reminder",
                "Upcoming club meeting next week",
                "1 day ago"
        ));
        return notifications;
    }
}