package com.example.sscapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.adapters.QuickAccessAdapter;
import com.example.sscapp.adapters.ServicesAdapter;
import com.example.sscapp.adapters.ServiceUsageAdapter;
import com.example.sscapp.models.QuickLink;
import com.example.sscapp.models.Service;
import com.example.sscapp.models.ServiceUsage;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment implements QuickAccessAdapter.OnQuickLinkClickListener {

    private LinearLayout adminQuickAccessContainer;
    private RecyclerView adminServicesRecyclerView;
    private RecyclerView serviceUsageRecyclerView;
    private TextView tvTotalUsers, tvPaidMemberships;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_home, container, false);


        // Initialize Views
        adminQuickAccessContainer = view.findViewById(R.id.admin_quickAccessContainer);
        adminServicesRecyclerView = view.findViewById(R.id.admin_servicesRecyclerView);
        serviceUsageRecyclerView = view.findViewById(R.id.service_usage_recycler);
        tvTotalUsers = view.findViewById(R.id.tv_total_users);
        tvPaidMemberships = view.findViewById(R.id.tv_paid_memberships);

        // Initialize Dashboard Components
        setupUserStatistics();
        setupServiceUsageRecycler();
        setupAdminQuickAccess();
        setupAdminServices();

        return view;
    }

    private void setupUserStatistics() {
        tvTotalUsers.setText(String.valueOf(1245));
        tvPaidMemberships.setText(String.valueOf(876));
    }

    private void setupServiceUsageRecycler() {
        List<ServiceUsage> serviceUsages = new ArrayList<>();
        serviceUsages.add(new ServiceUsage("CALagapay", 345, R.drawable.ic_calculator));
        serviceUsages.add(new ServiceUsage("Project Agapay", 278, R.drawable.ic_file_text));
        serviceUsages.add(new ServiceUsage("Zoom Connect", 212, R.drawable.ic_meeting));
        serviceUsages.add(new ServiceUsage("eSSCentials", 189, R.drawable.ic_radio));
        serviceUsages.add(new ServiceUsage("ReSSCue", 166, R.drawable.ic_assistance));

        ServiceUsageAdapter adapter = new ServiceUsageAdapter(requireContext(), serviceUsages);
        serviceUsageRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        serviceUsageRecyclerView.setAdapter(adapter);
    }

    private void setupAdminQuickAccess() {
        List<QuickLink> quickLinks = new ArrayList<>();

        quickLinks.add(new QuickLink("User Management", R.drawable.ic_users, "/admin/users", false));
        quickLinks.add(new QuickLink("Payment Tracking", R.drawable.ic_payment, "/admin/payments", false));
        quickLinks.add(new QuickLink("Lost and Found", R.drawable.ic_search, "/admin/lostFound", false));
        quickLinks.add(new QuickLink("Service Reports", R.drawable.ic_report, "/admin/service-reports", false));

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        LinearLayout currentRow = null;

        for (int i = 0; i < quickLinks.size(); i++) {
            if (i % 2 == 0) {
                currentRow = new LinearLayout(requireContext());
                currentRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                currentRow.setOrientation(LinearLayout.HORIZONTAL);
                currentRow.setWeightSum(2);
                adminQuickAccessContainer.addView(currentRow);
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
            case "/admin/users":
                intent = new Intent(requireContext(), AdminUserManagementActivity.class);
                break;
            case "/admin/service-reports":
                intent = new Intent(requireContext(), AdminServiceReportsActivity.class);
                break;
            case "/admin/lostFound":
                intent = new Intent(requireContext(), AdminLostFoundActivity.class);
                break;
            case "/admin/analytics":
                intent = new Intent(requireContext(), AdminAnalyticsActivity.class);
                break;
            default:
                Toast.makeText(requireContext(), "Feature not implemented", Toast.LENGTH_SHORT).show();
                return;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void setupAdminServices() {
        List<Service> services = new ArrayList<>();
        services.add(new Service(1, "Project Agapay", "Manage Printing Requests", R.drawable.ic_file_text, "Active"));
        services.add(new Service(2, "CALagapay", "Calculator Lending Management", R.drawable.ic_calculator, "Active"));
        services.add(new Service(3, "eSSCentials", "Radio Borrowing Tracking", R.drawable.ic_radio, "Active"));
        services.add(new Service(4, "ReSSCue", "Cash Assistance Monitoring", R.drawable.ic_assistance, "Active"));
        services.add(new Service(5, "Zoom Connect", "Virtual Event Support", R.drawable.ic_meeting, "Active"));

        ServicesAdapter adapter = new ServicesAdapter(requireContext(), services);
        adminServicesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adminServicesRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ServicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Service service) {
                switch (service.getTitle()) {
                    case "Project Agapay":
                        startActivity(new Intent(requireContext(), AdminProjectAgapayActivity.class));
                        break;
                    case "CALagapay":
                        startActivity(new Intent(requireContext(), AdminCALagapayActivity.class));
                        break;
                    case "ReSSCue":
                        startActivity(new Intent(requireContext(), AdminReSSCueActivity.class));
                        break;
                    case "eSSCentials":
                        startActivity(new Intent(requireContext(), AdmineSSCentialsActivity.class));
                        break;
                    case "Zoom Connect":
                        startActivity(new Intent(requireContext(), AdminZoomConnectActivity.class));
                        break;
                }
            }
        });
    }
}

