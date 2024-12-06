package com.example.sscapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.CartItem;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private static final String TAG = "OrderItemAdapter";
    private List<CartItem> items;
    private NumberFormat currencyFormatter;

    public OrderItemAdapter(List<CartItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        Log.d(TAG, "OrderItemAdapter created with " + this.items.size() + " items");
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouped_cart, parent, false);
        Log.d(TAG, "OrderItemViewHolder created");
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        CartItem item = items.get(position);
        Log.d(TAG, "Binding item at position " + position + ": " + item.getName());
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productQuantity, productPrice;

        OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
        }

        void bind(CartItem item) {
            if (item != null) {
                productName.setText(item.getName());
                productQuantity.setText(String.format("Qty: %d", item.getQuantity()));
                productPrice.setText(currencyFormatter.format(item.getPrice() * item.getQuantity()));
                Log.d(TAG, "Bound item: " + item.getName() + ", Qty: " + item.getQuantity() + ", Price: " + (item.getPrice() * item.getQuantity()));
            } else {
                Log.e(TAG, "Attempted to bind null item");
            }
        }
    }
}