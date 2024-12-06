package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.Order;
import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder> {

    private List<Order> orders;
    private boolean showVerifyButton;
    private OnOrderActionListener listener;

    public interface OnOrderActionListener {
        void onVerifyClicked(Order order, int position);
        void onDeleteClicked(Order order, int position);
    }

    public AdminOrderAdapter(List<Order> orders, boolean showVerifyButton, OnOrderActionListener listener) {
        this.orders = orders;
        this.showVerifyButton = showVerifyButton;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_order, parent, false);
        return new AdminOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order, position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public List<Order> getOrders() {
        return orders;
    }

    class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView srCodeTextView;
        TextView dateTextView;
        TextView statusTextView;
        TextView totalAmountTextView;
        ImageButton deleteButton;
        ImageButton verifyButton;
        RecyclerView itemsRecyclerView;

        AdminOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            srCodeTextView = itemView.findViewById(R.id.sr_code);
            dateTextView = itemView.findViewById(R.id.order_date);
            statusTextView = itemView.findViewById(R.id.order_status);
            totalAmountTextView = itemView.findViewById(R.id.order_total);
            deleteButton = itemView.findViewById(R.id.btn_delete_order);
            verifyButton = itemView.findViewById(R.id.btn_confirm_order);
            itemsRecyclerView = itemView.findViewById(R.id.items_recycler_view);
        }

        void bind(final Order order, final int position) {
            orderIdTextView.setText(order.getOrderId());
            srCodeTextView.setText(order.getSrCode());
            dateTextView.setText(order.getDate());
            statusTextView.setText(order.getStatus());
            totalAmountTextView.setText(String.format("₱%.2f", order.getTotalAmount()));

            // Set the background color based on the order status
            switch (order.getStatus().toLowerCase()) {
                case "processing":
                    statusTextView.setBackgroundResource(R.drawable.status_background_yellow);
                    break;
                case "verified":
                    statusTextView.setBackgroundResource(R.drawable.status_background);
                    statusTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
                    break;
                default:
                    statusTextView.setBackgroundResource(R.drawable.status_background);
                    break;
            }


            GroupedItemAdapter groupedItemAdapter = new GroupedItemAdapter(order.getItems());
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            itemsRecyclerView.setAdapter(groupedItemAdapter);

            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClicked(order, position);
                }
            });

            if (showVerifyButton) {
                verifyButton.setVisibility(View.VISIBLE);
                verifyButton.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onVerifyClicked(order, position);
                    }
                });
            } else {
                verifyButton.setVisibility(View.GONE);
            }
        }
    }
}

