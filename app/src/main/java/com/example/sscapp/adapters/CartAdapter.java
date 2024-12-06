package com.example.sscapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sscapp.R;
import com.example.sscapp.CartManager;
import com.example.sscapp.models.CartItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onCartItemChanged();
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productQuantity;
        TextView productSize;
        MaterialButton removeItem;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productSize = itemView.findViewById(R.id.product_size);
            removeItem = itemView.findViewById(R.id.remove_item);
        }

        @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
        void bind(CartItem item) {
            productName.setText(item.getName());
            productPrice.setText(String.format("₱%.2f", item.getPrice()));
            productQuantity.setText(String.valueOf(item.getQuantity()));
            productSize.setText(String.format("‧  Size: %s", item.getSize()));

            Context context = itemView.getContext();
            Glide.with(context)
                    .load(item.getImageResId())
                    .fitCenter()
                    .into(productImage);

            itemView.findViewById(R.id.increase_quantity).setOnClickListener(v -> {
                item.setQuantity(item.getQuantity() + 1);
                notifyDataSetChanged();
                listener.onCartItemChanged();
            });

            itemView.findViewById(R.id.decrease_quantity).setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    notifyDataSetChanged();
                    listener.onCartItemChanged();
                }
            });

            removeItem.setOnClickListener(v -> {
                CartManager.getInstance().removeFromCart(item);
                cartItems.remove(item);
                notifyDataSetChanged();
                listener.onCartItemChanged();
            });
        }
    }
}
