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
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.activities.CALagapay;
import com.example.sscapp.activities.ZoomConnect;
import com.example.sscapp.activities.eSSCentials;
import com.example.sscapp.activities.reSSCue;
import com.example.sscapp.adapters.AnnouncementsAdapter;
import com.example.sscapp.adapters.QuickAccessAdapter;
import com.example.sscapp.adapters.ServicesAdapter;
import com.example.sscapp.models.Announcement;
import com.example.sscapp.models.QuickLink;
import com.example.sscapp.models.Service;
import com.example.sscapp.quickaccesscard.EventTimelineActivity;
import com.example.sscapp.quickaccesscard.LostAndFoundActivity;
import com.example.sscapp.quickaccesscard.MembershipPaymentActivity;
import com.example.sscapp.utils.CarouselLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements QuickAccessAdapter.OnQuickLinkClickListener {

    private static final int INITIAL_POSITION = Integer.MAX_VALUE / 2;
    private RecyclerView announcementsRecyclerView;
    private LinearLayout quickAccessContainer;
    private RecyclerView servicesRecyclerView;
    private List<Announcement> pinnedAnnouncements;
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

    private List<Announcement> getAnnouncementsFromUpdatesFragment() {
        UpdatesFragment updatesFragment = new UpdatesFragment();
        return updatesFragment.getAnnouncements();
    }

    private void setupAnnouncements() {
        List<Announcement> allAnnouncements = getAnnouncementsFromUpdatesFragment();
        pinnedAnnouncements = new ArrayList<>();

        for (Announcement announcement : allAnnouncements) {
            if (announcement.isPinnedAttachment()) {
                pinnedAnnouncements.add(announcement);
            }
        }

        AnnouncementsAdapter adapter = new AnnouncementsAdapter(pinnedAnnouncements);

        CarouselLayoutManager layoutManager = new CarouselLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        announcementsRecyclerView.setLayoutManager(layoutManager);
        announcementsRecyclerView.setAdapter(adapter);

        int padding = getResources().getDimensionPixelOffset(R.dimen.carousel_padding);
        announcementsRecyclerView.setPadding(padding, 0, padding, 0);
        announcementsRecyclerView.setClipToPadding(false);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(announcementsRecyclerView);

        adapter.setItemCount(Integer.MAX_VALUE);

        int firstAnnouncementPosition = INITIAL_POSITION - (INITIAL_POSITION % pinnedAnnouncements.size());
        announcementsRecyclerView.scrollToPosition(firstAnnouncementPosition);
        currentAnnouncementPosition = firstAnnouncementPosition;

        announcementsRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                announcementsRecyclerView.smoothScrollToPosition(firstAnnouncementPosition);
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
        }, 5000);
    }

    private void stopAutoScroll() {
        if (autoScrollHandler != null) {
            autoScrollHandler.removeCallbacksAndMessages(null);
        }
    }

    private void setupQuickAccess() {
        List<QuickLink> quickLinks = new ArrayList<>();

        quickLinks.add(new QuickLink("Membership", R.drawable.ic_credit_card, "/membership", true));
        quickLinks.add(new QuickLink("Events Timeline", R.drawable.ic_timeline, "/events", false));
        quickLinks.add(new QuickLink("Lost & Found", R.drawable.ic_search, "/lost-found", false));
        quickLinks.add(new QuickLink("Emergency Contacts", R.drawable.ic_contact_emergency, "/contacts", false));

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
                currentRow.setWeightSum(2);
                quickAccessContainer.addView(currentRow);
            }

            View itemView = inflater.inflate(R.layout.item_quick_access, currentRow, false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            int margin = getResources().getDimensionPixelSize(R.dimen.quick_access_margin);
            params.setMargins(margin, margin, margin, margin);
            itemView.setLayoutParams(params);

            QuickAccessAdapter.ViewHolder viewHolder = new QuickAccessAdapter.ViewHolder(itemView);
            QuickAccessAdapter.bindViewHolder(viewHolder, quickLinks.get(i), this);
            currentRow.addView(itemView);
        }
    }

    @Override
    public void onQuickLinkClick(QuickLink quickLink) {
        Intent intent = null;

        switch (quickLink.getLink()) {
            case "/membership":
                if (quickLink.isMembershipCard()) {
                    intent = new Intent(getContext(), MembershipPaymentActivity.class);
                }
                break;

            case "/events":
                intent = new Intent(getContext(), EventTimelineActivity.class);
                break;

            case "/lost-found":
                intent = new Intent(getContext(), LostAndFoundActivity.class);
                break;

            case "/contacts":
                Toast.makeText(getContext(), "Contacts feature coming soon!", Toast.LENGTH_SHORT).show();
                return;

            default:
                Toast.makeText(getContext(), "Unknown link: " + quickLink.getLink(), Toast.LENGTH_SHORT).show();
                return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void setupServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service(1, "Project Agapay", "Affordable printing services", R.drawable.ic_file_text, "Available"));
        services.add(new Service(2, "CALagapay", "Calculator lending service", R.drawable.ic_calculator, "Limited"));
        services.add(new Service(3, "eSSCentials", "Walkie-talkie borrowing service", R.drawable.ic_radio, "Available"));
        services.add(new Service(4, "ReSSCue", "Cash assistance lending service", R.drawable.ic_assistance, "Available"));
        services.add(new Service(5, "Zoom Connect", "Virtual event support service", R.drawable.ic_meeting, "Available"));

        ServicesAdapter adapter = new ServicesAdapter(getContext(), services);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        servicesRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ServicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Service service) {
                switch (service.getTitle()) {
                    case "Project Agapay":
                        startActivity(new Intent(getContext(), ProjectAgapayActivity.class));
                        break;
                    case "CALagapay":
                        startActivity(new Intent(getContext(), CALagapay.class));
                        break;
                    case "ReSSCue":
                        startActivity(new Intent(getContext(), reSSCue.class));
                        break;
                    case "eSSCentials":
                        startActivity(new Intent(getContext(), eSSCentials.class));
                        break;
                    case "Zoom Connect":
                        startActivity(new Intent(getContext(), ZoomConnect.class));
                        break;
                }
            }
        });
    }
}

