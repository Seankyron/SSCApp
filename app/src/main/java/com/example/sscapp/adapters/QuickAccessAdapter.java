package com.example.sscapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.sscapp.R;
import com.example.sscapp.models.QuickLink;

public class QuickAccessAdapter {

    public interface OnQuickLinkClickListener {
        void onQuickLinkClick(QuickLink quickLink);
    }

    public static void bindViewHolder(ViewHolder holder, QuickLink quickLink, OnQuickLinkClickListener listener) {
        holder.title.setText(quickLink.getTitle());
        holder.icon.setImageResource(quickLink.getIconResId());

        // Add ripple effect with click handling
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                // Simple scale animation
                v.animate()
                        .scaleX(0.97f)
                        .scaleY(0.97f)
                        .setDuration(100)
                        .withEndAction(() -> {
                            v.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(100)
                                    .start();
                            listener.onQuickLinkClick(quickLink);
                        })
                        .start();
            }
        });
    }

    public static class ViewHolder {
        final ImageView icon;
        final TextView title;
        final View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
        }
    }
}