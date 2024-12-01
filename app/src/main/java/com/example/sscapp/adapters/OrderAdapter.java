package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.GroupedCartItem;
import com.example.sscapp.models.Order;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView dateTextView;
        TextView statusTextView;
        TextView totalAmountTextView;
        RecyclerView itemsRecyclerView;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            dateTextView = itemView.findViewById(R.id.order_date);
            statusTextView = itemView.findViewById(R.id.order_status);
            totalAmountTextView = itemView.findViewById(R.id.order_total);
            itemsRecyclerView = itemView.findViewById(R.id.items_recycler_view);
        }

        void bind(Order order) {
            orderIdTextView.setText(order.getOrderId());
            dateTextView.setText(order.getDate());
            statusTextView.setText(order.getStatus());
            totalAmountTextView.setText(String.format("Total: ₱%.2f", order.getTotalAmount()));

            GroupedItemAdapter groupedItemAdapter = new GroupedItemAdapter(order.getItems());
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            itemsRecyclerView.setAdapter(groupedItemAdapter);
        }
    }
}