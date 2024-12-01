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
import com.example.sscapp.adapters.OrderAdapter;
import com.example.sscapp.models.Order;
import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView ordersRecyclerView;
    private List<Order> orders;
    private OrderAdapter orderAdapter;
    private View emptyOrdersView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        ordersRecyclerView = view.findViewById(R.id.orders_recycler_view);
        emptyOrdersView = view.findViewById(R.id.empty_order_view);

        setupOrders();
        setupRecyclerView();

        return view;
    }

    private void setupOrders() {
        orders = OrderManager.getInstance().getOrders();
        updateOrdersView();
    }

    private void setupRecyclerView() {
        List<Order> orders = OrderManager.getInstance().getOrders();
        orderAdapter = new OrderAdapter(orders);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersRecyclerView.setAdapter(orderAdapter);
    }

    private void updateOrdersView() {
        if (orders.isEmpty()) {
            emptyOrdersView.setVisibility(View.VISIBLE);
            ordersRecyclerView.setVisibility(View.GONE);
        } else {
            emptyOrdersView.setVisibility(View.GONE);
            ordersRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOrders();
        orderAdapter.notifyDataSetChanged();
        updateOrdersView();
    }
}