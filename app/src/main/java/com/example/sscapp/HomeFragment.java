package com.example.sscapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.adapters.AnnouncementsAdapter;
import com.example.sscapp.adapters.QuickAccessAdapter;
import com.example.sscapp.adapters.ServicesAdapter;
import com.example.sscapp.models.Announcement;
import com.example.sscapp.models.QuickLink;
import com.example.sscapp.models.Service;
import com.example.sscapp.quickaccesscard.MembershipPaymentActivity;
import com.example.sscapp.utils.CarouselLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements QuickAccessAdapter.OnQuickLinkClickListener {

    private static final int INITIAL_POSITION = Integer.MAX_VALUE / 2;
    private RecyclerView announcementsRecyclerView;
    private LinearLayout quickAccessContainer;
    private RecyclerView servicesRecyclerView;
    private List<Announcement> announcements;
    private Handler autoScrollHandler;
    private int currentAnnouncementPosition = 0;

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

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    private void setupAnnouncements() {
        announcements = new ArrayList<>();
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

        CarouselLayoutManager layoutManager = new CarouselLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        announcementsRecyclerView.setLayoutManager(layoutManager);
        announcementsRecyclerView.setAdapter(adapter);

        // Add padding to show part of the next and previous items
        int padding = getResources().getDimensionPixelOffset(R.dimen.carousel_padding);
        announcementsRecyclerView.setPadding(padding, 0, padding, 0);
        announcementsRecyclerView.setClipToPadding(false);

        // Add PagerSnapHelper to snap to the center
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(announcementsRecyclerView);

        // Set up infinite scrolling
        adapter.setItemCount(Integer.MAX_VALUE);

        // Calculate the position that will show the first announcement in the center
        int firstAnnouncementPosition = INITIAL_POSITION - (INITIAL_POSITION % announcements.size());
        announcementsRecyclerView.scrollToPosition(firstAnnouncementPosition);
        currentAnnouncementPosition = firstAnnouncementPosition;

        // Add a global layout listener to ensure the scroll happens after layout
        announcementsRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                announcementsRecyclerView.smoothScrollToPosition(firstAnnouncementPosition);
                // Remove the listener to prevent multiple calls
                announcementsRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void startAutoScroll() {
        autoScrollHandler = new Handler();
        autoScrollHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentAnnouncementPosition++;
                announcementsRecyclerView.smoothScrollToPosition(currentAnnouncementPosition);
                autoScrollHandler.postDelayed(this, 5000); // Scroll every 5 seconds
            }
        }, 5000); // Start after 5 seconds
    }

    private void stopAutoScroll() {
        if (autoScrollHandler != null) {
            autoScrollHandler.removeCallbacksAndMessages(null);
        }
    }


    private void setupQuickAccess() {
        List<QuickLink> quickLinks = new ArrayList<>();
        quickLinks.add(new QuickLink("Membership", R.drawable.ic_credit_card, "/membership", true));
        quickLinks.add(new QuickLink("Events", R.drawable.ic_calendar, "/events", false));
        quickLinks.add(new QuickLink("Lost & Found", R.drawable.ic_search, "/lost-found", false));
        quickLinks.add(new QuickLink("Campus Map", R.drawable.ic_contact_emergency, "/map", false));

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

    @Override
    public void onQuickLinkClick(QuickLink quickLink) {
        if (quickLink.isMembershipCard()) {
            Intent intent = new Intent(getContext(), MembershipPaymentActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Clicked: " + quickLink.getTitle(), Toast.LENGTH_SHORT).show();
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

}

