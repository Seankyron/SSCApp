package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.adapters.AnnouncementsAdapter;
import com.example.sscapp.adapters.QuickAccessAdapter;
import com.example.sscapp.adapters.ServicesAdapter;
import com.example.sscapp.models.Announcement;
import com.example.sscapp.models.QuickLink;
import com.example.sscapp.models.Service;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements QuickAccessAdapter.OnQuickLinkClickListener {

    private RecyclerView announcementsRecyclerView;
    private LinearLayout quickAccessContainer;
    private RecyclerView servicesRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        announcementsRecyclerView = view.findViewById(R.id.announcementsRecyclerView);
        quickAccessContainer = view.findViewById(R.id.quickAccessContainer);
        servicesRecyclerView = view.findViewById(R.id.servicesRecyclerView);

        setupAnnouncements();
        setupQuickAccess();
        setupServices();

        return view;
    }

    private void setupAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement(1, "Membership Payment Deadline",
                "Last day of payment is on November 30, 2024", "urgent",
                "Deadline", "SSC Treasury", "/api/placeholder/800/400"));
        announcements.add(new Announcement(2, "Take Off V.4.0",
                "First Year Student Orientation on August 15-16, 2024", "important",
                "Event", "SSC Events Committee", "/api/placeholder/800/400"));
        announcements.add(new Announcement(3, "Froshie Fair V.6.0",
                "Join us on September 30, 2024", "important",
                "Event", "SSC Events Committee", "/api/placeholder/800/400"));

        AnnouncementsAdapter adapter = new AnnouncementsAdapter(announcements);

        announcementsRecyclerView.setClipToPadding(false);
        announcementsRecyclerView.setPadding(8, 0, 8, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        announcementsRecyclerView.setLayoutManager(layoutManager);
        announcementsRecyclerView.setAdapter(adapter);
    }

    private void setupQuickAccess() {
        List<QuickLink> quickLinks = new ArrayList<>();
        quickLinks.add(new QuickLink("Membership", R.drawable.ic_credit_card, "/membership"));
        quickLinks.add(new QuickLink("Events", R.drawable.ic_calendar, "/events"));
        quickLinks.add(new QuickLink("Lost & Found", R.drawable.ic_search, "/lost-found"));
        quickLinks.add(new QuickLink("Campus Map", R.drawable.ic_contact_emergency, "/map"));

        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout currentRow = null;

        for (int i = 0; i < quickLinks.size(); i++) {
            if (i % 2 == 0) {
                currentRow = new LinearLayout(getContext());
                currentRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                currentRow.setOrientation(LinearLayout.HORIZONTAL);
                quickAccessContainer.addView(currentRow);
            }

            View itemView = inflater.inflate(R.layout.item_quick_access, currentRow, false);
            QuickAccessAdapter.ViewHolder viewHolder = new QuickAccessAdapter.ViewHolder(itemView);
            QuickAccessAdapter.bindViewHolder(viewHolder, quickLinks.get(i), this);
            currentRow.addView(itemView);
        }
    }

    private void setupServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service(1, "Project Agapay", "Affordable printing services", R.drawable.ic_file_text, "Available"));
        services.add(new Service(2, "CALagapay", "Calculator lending service", R.drawable.ic_calculator, "Limited"));
        services.add(new Service(3, "eSSCentials", "Walkie-talkie borrowing service", R.drawable.ic_radio, "Available"));

        ServicesAdapter adapter = new ServicesAdapter(services);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        servicesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onQuickLinkClick(QuickLink quickLink) {
        // Handle the click event for quick access items
        Toast.makeText(getContext(), "Clicked: " + quickLink.getTitle(), Toast.LENGTH_SHORT).show();
        // You can add navigation logic here based on quickLink.getLink()
    }
}

