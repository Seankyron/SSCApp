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

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.AdminProductViewHolder> {

    private final List<Product> products;
    private final OnProductClickListener listener;
    private final NumberFormat currencyFormatter;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public AdminProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
    }

    @NonNull
    @Override
    public AdminProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_item_product, parent, false);
        return new AdminProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class AdminProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        private final TextView productDescription;
        private final TextView productStatus;

        AdminProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productDescription = itemView.findViewById(R.id.product_description);
            productStatus = itemView.findViewById(R.id.product_status);

            // We don't set an OnClickListener here as we don't want to navigate to ProductDetailsFragment
        }

        void bind(Product product) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(currencyFormatter.format(product.getPrice()));

            // Set the status
            if (product.getStatus() != null && !product.getStatus().isEmpty()) {
                productStatus.setText(product.getStatus());
                productStatus.setVisibility(View.VISIBLE);

                // Set status color based on the status value
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
            } else {
                productStatus.setVisibility(View.GONE);
            }
        }
    }
}