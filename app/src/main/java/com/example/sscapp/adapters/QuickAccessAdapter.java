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

    public static class ViewHolder {
        public final View itemView;
        public final ImageView icon;
        public final TextView title;

        public ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
            icon = itemView.findViewById(R.id.quickAccessIcon);
            title = itemView.findViewById(R.id.quickAccessTitle);
        }
    }

    public static void bindViewHolder(@NonNull ViewHolder holder, QuickLink quickLink, OnQuickLinkClickListener listener) {
        holder.icon.setImageResource(quickLink.getIconResId());
        holder.title.setText(quickLink.getTitle());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuickLinkClick(quickLink);
            }
        });
    }
}

