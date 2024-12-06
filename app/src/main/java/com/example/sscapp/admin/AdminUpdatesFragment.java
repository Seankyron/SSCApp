package com.example.sscapp.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.UpdatesFragment;
import com.example.sscapp.adapters.AdminUpdatesAdapter;
import com.example.sscapp.adapters.OnAnnouncementActionListener;
import com.example.sscapp.models.Announcement;
import java.util.ArrayList;
import java.util.List;

public class AdminUpdatesFragment extends Fragment implements OnAnnouncementActionListener {

    private RecyclerView pinnedRecyclerView;
    private RecyclerView recentRecyclerView;
    private AdminUpdatesAdapter pinnedAdapter;
    private AdminUpdatesAdapter recentAdapter;
    private List<Announcement> allAnnouncements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_updates, container, false);

        pinnedRecyclerView = view.findViewById(R.id.pinnedUpdatesRecyclerView);
        recentRecyclerView = view.findViewById(R.id.recentUpdatesRecyclerView);

        pinnedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        allAnnouncements = new ArrayList<>(UpdatesFragment.getStaticAnnouncements());
        updateAnnouncementLists();

        return view;
    }

    private void updateAnnouncementLists() {
        List<Announcement> pinnedAnnouncements = new ArrayList<>();
        List<Announcement> recentAnnouncements = new ArrayList<>();

        for (Announcement announcement : allAnnouncements) {
            if (announcement.isPinnedAttachment()) {
                pinnedAnnouncements.add(announcement);
            } else {
                recentAnnouncements.add(announcement);
            }
        }

        pinnedAdapter = new AdminUpdatesAdapter(pinnedAnnouncements, this);
        recentAdapter = new AdminUpdatesAdapter(recentAnnouncements, this);

        pinnedRecyclerView.setAdapter(pinnedAdapter);
        recentRecyclerView.setAdapter(recentAdapter);
    }

    @Override
    public void onPinClicked(Announcement announcement, int position) {
        announcement.setPinnedAttachment(true);
        updateAnnouncementLists();
    }

    @Override
    public void onDeleteClicked(Announcement announcement, int position) {
        allAnnouncements.remove(announcement);
        updateAnnouncementLists();
    }
}