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
        announcements.add(new Announcement(1, "Froshie Fair V.6.0", "A vibrant welcome celebration for our freshmen students. Experience campus culture, join student organizations, and create lasting friendships.", "Important", "Event Updates", "Student Affairs Office", "https://scontent.fmnl13-2.fna.fbcdn.net/v/t39.30808-6/460965587_959404372880776_2901834559992137466_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeFMfuNuYtql93dP0qcEWzXkeTDYJNnJNe95MNgk2ck17-BeBPWNLjQh_bk1NbhLswhsPR9rm9WSNmbldsWGBZf_&_nc_ohc=GC-7MPs6c-UQ7kNvgHZAIKZ&_nc_zt=23&_nc_ht=scontent.fmnl13-2.fna&_nc_gid=AWYQxNcSyV9aFm2U6xQ8kWT&oh=00_AYCemeBOkDbFft5ieP9c9LQzB2iu1FXbyYygqPiyUljSVQ&oe=6758F67F", true, "2024-09-30"));
        announcements.add(new Announcement(2, "Dean's Lister Recognition Day", "Congratulations to all students who made it to the Dean's List!", "Important", "Academic Achievements", "Office of the Dean", "https://example.com/deans-list.jpg", false, "2024-05-20"));
        announcements.add(new Announcement(3, "Class Suspension Advisory", "All classes are suspended today due to inclement weather.", "Urgent", "Weather Advisory", "Office of the President", "https://scontent.fmnl13-4.fna.fbcdn.net/v/t39.30808-6/466379762_995936725894207_1095602654923413313_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeFeLGRJjM2vk95RdQk_e04ubtRk3qOZjRJu1GTeo5mNEnEkORrOFRWBKdVGa2YRKZAQeH8eL4aLvCzWc8o5Ffjl&_nc_ohc=8uytNo9fTNsQ7kNvgED4Wdr&_nc_zt=23&_nc_ht=scontent.fmnl13-4.fna&_nc_gid=APliwRWjAYkQuaPqo8Q30nS&oh=00_AYDQI2UoYbRsRnyQBzxCVleINTLRcGLiRcOcCMrreambcA&oe=6758DD89", true, "2024-11-11"));
        announcements.add(new Announcement(4, "New Laboratory Equipment", "College of Engineering receives new state-of-the-art equipment", "Normal", "Campus News", "College of Engineering", "https://example.com/new-equipment.jpg", false, "2024-05-22"));
        return announcements;
    }

    public static List<Announcement> getStaticAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement(1, "Froshie Fair V.6.0", "A vibrant welcome celebration for our freshmen students. Experience campus culture, join student organizations, and create lasting friendships.", "Important", "Event Updates", "Student Affairs Office", "https://scontent.fmnl13-2.fna.fbcdn.net/v/t39.30808-6/460965587_959404372880776_2901834559992137466_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeFMfuNuYtql93dP0qcEWzXkeTDYJNnJNe95MNgk2ck17-BeBPWNLjQh_bk1NbhLswhsPR9rm9WSNmbldsWGBZf_&_nc_ohc=GC-7MPs6c-UQ7kNvgHZAIKZ&_nc_zt=23&_nc_ht=scontent.fmnl13-2.fna&_nc_gid=AWYQxNcSyV9aFm2U6xQ8kWT&oh=00_AYCemeBOkDbFft5ieP9c9LQzB2iu1FXbyYygqPiyUljSVQ&oe=6758F67F", true, "2024-09-30"));
        announcements.add(new Announcement(2, "Dean's Lister Recognition Day", "Congratulations to all students who made it to the Dean's List!", "Important", "Academic Achievements", "Office of the Dean", "https://example.com/deans-list.jpg", false, "2024-05-20"));
        announcements.add(new Announcement(3, "Class Suspension Advisory", "All classes are suspended today due to inclement weather.", "Urgent", "Weather Advisory", "Office of the President", "https://scontent.fmnl13-4.fna.fbcdn.net/v/t39.30808-6/466379762_995936725894207_1095602654923413313_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeFeLGRJjM2vk95RdQk_e04ubtRk3qOZjRJu1GTeo5mNEnEkORrOFRWBKdVGa2YRKZAQeH8eL4aLvCzWc8o5Ffjl&_nc_ohc=8uytNo9fTNsQ7kNvgED4Wdr&_nc_zt=23&_nc_ht=scontent.fmnl13-4.fna&_nc_gid=APliwRWjAYkQuaPqo8Q30nS&oh=00_AYDQI2UoYbRsRnyQBzxCVleINTLRcGLiRcOcCMrreambcA&oe=6758DD89", true, "2024-11-11"));
        announcements.add(new Announcement(4, "New Laboratory Equipment", "College of Engineering receives new state-of-the-art equipment", "Normal", "Campus News", "College of Engineering", "https://example.com/new-equipment.jpg", false, "2024-05-22"));
        return announcements;
    }
}
