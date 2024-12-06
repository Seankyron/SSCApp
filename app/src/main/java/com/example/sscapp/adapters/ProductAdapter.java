package com.example.sscapp.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sscapp.R;
import com.example.sscapp.models.Product;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final List<Product> products;
    private final OnProductClickListener listener;
    private final NumberFormat currencyFormatter;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catalog, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        private final TextView productDescription;
        private final TextView productStatus;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productStatus = itemView.findViewById(R.id.productStatus);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onProductClick(products.get(position));
                }
            });
        }

        void bind(Product product) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(currencyFormatter.format(product.getPrice()));

            int statusColor;
            switch (product.getStatus().toLowerCase()) {
                case "limited":
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.yellow_dark);
                    break;
                case "out-of-stock":
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.red_primary);
                    break;
                default:
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.green);
                    break;
            }

            GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_service_status);
            if (drawable != null) {
                drawable.setColor(statusColor);
                productStatus.setBackground(drawable);
            }

            Context context = itemView.getContext();
            Glide.with(context)
                    .load(product.getImageResId())
                    .fitCenter()
                    .into(productImage);

            // Set the status
            if (product.getStatus() != null && !product.getStatus().isEmpty()) {
                productStatus.setText(product.getStatus());
                productStatus.setVisibility(View.VISIBLE);
            } else {
                productStatus.setVisibility(View.GONE);
            }
        }
    }
}

