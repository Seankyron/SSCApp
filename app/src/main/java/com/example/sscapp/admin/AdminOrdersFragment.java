package com.example.sscapp.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.adapters.AdminOrderAdapter;
import com.example.sscapp.models.GroupedCartItem;
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
    private TextView processingOrderText;
    private TextView verifiedOrderText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_orders, container, false);

        processingOrdersRecyclerView = view.findViewById(R.id.processingOrdersRecyclerView);
        verifiedOrdersRecyclerView = view.findViewById(R.id.verifiedOrdersRecyclerView);
        emptyOrderView = view.findViewById(R.id.empty_order_view);

        processingOrderText = view.findViewById(R.id.processingOrdersTitle);
        verifiedOrderText = view.findViewById(R.id.verifiedOrdersTitle);

        processingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        verifiedOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupOrders();
        updateOrdersView();

        return view;
    }

    private void setupOrders() {
        // This is where you would normally fetch orders from a database or API
        // For this example, we'll create some sample orders
        processingOrders = createSampleProcessingOrders();
        verifiedOrders = createSampleVerifiedOrders();

        processingOrderAdapter = new AdminOrderAdapter(processingOrders, true, new AdminOrderAdapter.OnOrderActionListener() {
            @Override
            public void onVerifyClicked(Order order, int position) {
                onOrderVerified(order);
            }

            @Override
            public void onDeleteClicked(Order order, int position) {
                onOrderDeleted(order);
            }
        });

        verifiedOrderAdapter = new AdminOrderAdapter(verifiedOrders, false, new AdminOrderAdapter.OnOrderActionListener() {
            @Override
            public void onVerifyClicked(Order order, int position) {
                // Do nothing for verified orders
            }

            @Override
            public void onDeleteClicked(Order order, int position) {
                onOrderDeleted(order);
            }
        });

        processingOrdersRecyclerView.setAdapter(processingOrderAdapter);
        verifiedOrdersRecyclerView.setAdapter(verifiedOrderAdapter);

        updateOrdersView();
    }


    private void updateOrdersView() {
        if (processingOrders.isEmpty() && verifiedOrders.isEmpty()) {
            emptyOrderView.setVisibility(View.VISIBLE);
            processingOrdersRecyclerView.setVisibility(View.GONE);
            verifiedOrdersRecyclerView.setVisibility(View.GONE);
            processingOrderText.setVisibility(View.GONE);
            verifiedOrderText.setVisibility(View.GONE);
        } else {
            emptyOrderView.setVisibility(View.GONE);
            processingOrdersRecyclerView.setVisibility(View.VISIBLE);
            verifiedOrdersRecyclerView.setVisibility(View.VISIBLE);
            processingOrderText.setVisibility(View.VISIBLE);
            verifiedOrderText.setVisibility(View.VISIBLE);
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
        // Create sample GroupedCartItems
        List<GroupedCartItem> groupedCartItems = new ArrayList<>();
        GroupedCartItem shirt1 = new GroupedCartItem("T-Shirt", 19.99, R.drawable.product_tshirt);
        shirt1.addSize("S", 2);
        shirt1.addSize("M", 1);
        groupedCartItems.add(shirt1);

        Log.d("GroupedCartItem", "CartItem: " + groupedCartItems);

        orders.add(new Order("ORD001", "22-08080", "2023-05-20", "Processing", 99.99, groupedCartItems));
        orders.add(new Order("ORD002", "22-08080", "2023-05-21", "Processing", 149.99, groupedCartItems));
        return orders;
    }

    private List<Order> createSampleVerifiedOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("ORD003", "22-08080", "2023-05-19", "Verified", 79.99, new ArrayList<>()));
        return orders;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOrders();
        updateOrdersView();
    }
}

