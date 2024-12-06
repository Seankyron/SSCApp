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
import com.example.sscapp.adapters.AdminOrderAdapter;
import com.example.sscapp.models.Order;
import java.util.ArrayList;
import java.util.List;

public class AdminOrdersFragment extends Fragment {

    private RecyclerView processingOrdersRecyclerView;
    private RecyclerView verifiedOrdersRecyclerView;
    private List<Order> processingOrders;
    private List<Order> verifiedOrders;
    private AdminOrderAdapter processingOrderAdapter;
    private AdminOrderAdapter verifiedOrderAdapter;
    private View emptyOrderView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_orders, container, false);

        processingOrdersRecyclerView = view.findViewById(R.id.processingOrdersRecyclerView);
        verifiedOrdersRecyclerView = view.findViewById(R.id.verifiedOrdersRecyclerView);
        emptyOrderView = view.findViewById(R.id.empty_order_view);

        setupOrders();
        setupRecyclerViews();

        return view;
    }

    private void setupOrders() {
        // This is where you would normally fetch orders from a database or API
        // For this example, we'll create some sample orders
        processingOrders = createSampleProcessingOrders();
        verifiedOrders = createSampleVerifiedOrders();
        updateOrdersView();
    }

    private void setupRecyclerViews() {
        processingOrderAdapter = new AdminOrderAdapter(processingOrders, true, this::onOrderVerified, this::onOrderDeleted);
        verifiedOrderAdapter = new AdminOrderAdapter(verifiedOrders, false, null, this::onOrderDeleted);

        processingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        processingOrdersRecyclerView.setAdapter(processingOrderAdapter);

        verifiedOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        verifiedOrdersRecyclerView.setAdapter(verifiedOrderAdapter);
    }

    private void updateOrdersView() {
        if (processingOrders.isEmpty() && verifiedOrders.isEmpty()) {
            emptyOrderView.setVisibility(View.VISIBLE);
            processingOrdersRecyclerView.setVisibility(View.GONE);
            verifiedOrdersRecyclerView.setVisibility(View.GONE);
        } else {
            emptyOrderView.setVisibility(View.GONE);
            processingOrdersRecyclerView.setVisibility(View.VISIBLE);
            verifiedOrdersRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void onOrderVerified(Order order) {
        processingOrders.remove(order);
        verifiedOrders.add(order);
        order.setStatus("Verified");
        processingOrderAdapter.notifyDataSetChanged();
        verifiedOrderAdapter.notifyDataSetChanged();
        updateOrdersView();
    }

    private void onOrderDeleted(Order order) {
        if (processingOrders.contains(order)) {
            processingOrders.remove(order);
            processingOrderAdapter.notifyDataSetChanged();
        } else if (verifiedOrders.contains(order)) {
            verifiedOrders.remove(order);
            verifiedOrderAdapter.notifyDataSetChanged();
        }
        updateOrdersView();
    }

    private List<Order> createSampleProcessingOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("ORD001", "2023-05-20", "Processing", 99.99, new ArrayList<>()));
        orders.add(new Order("ORD002", "2023-05-21", "Processing", 149.99, new ArrayList<>()));
        return orders;
    }

    private List<Order> createSampleVerifiedOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("ORD003", "2023-05-19", "Verified", 79.99, new ArrayList<>()));
        return orders;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOrders();
        processingOrderAdapter.notifyDataSetChanged();
        verifiedOrderAdapter.notifyDataSetChanged();
        updateOrdersView();
    }
}