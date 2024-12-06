package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.Order;
import java.util.List;
import java.util.function.Consumer;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder> {

    private List<Order> orders;
    private boolean showVerifyButton;
    private Consumer<Order> onVerifyClick;
    private Consumer<Order> onDeleteClick;

    public AdminOrderAdapter(List<Order> orders, boolean showVerifyButton, Consumer<Order> onVerifyClick, Consumer<Order> onDeleteClick) {
        this.orders = orders;
        this.showVerifyButton = showVerifyButton;
        this.onVerifyClick = onVerifyClick;
        this.onDeleteClick = onDeleteClick;
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
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView dateTextView;
        TextView statusTextView;
        TextView totalAmountTextView;
        ImageButton deleteButton;
        ImageButton verifyButton;

        AdminOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            dateTextView = itemView.findViewById(R.id.order_date);
            statusTextView = itemView.findViewById(R.id.order_status);
            totalAmountTextView = itemView.findViewById(R.id.order_total);
            deleteButton = itemView.findViewById(R.id.btn_delete_order);
            verifyButton = itemView.findViewById(R.id.btn_confirm_order);
        }

        void bind(Order order) {
            orderIdTextView.setText(order.getOrderId());
            dateTextView.setText(order.getDate());
            statusTextView.setText(order.getStatus());
            totalAmountTextView.setText(String.format("₱%.2f", order.getTotalAmount()));

            deleteButton.setOnClickListener(v -> onDeleteClick.accept(order));

            if (showVerifyButton) {
                verifyButton.setVisibility(View.VISIBLE);
                verifyButton.setOnClickListener(v -> onVerifyClick.accept(order));
            } else {
                verifyButton.setVisibility(View.GONE);
            }
        }
    }
}

