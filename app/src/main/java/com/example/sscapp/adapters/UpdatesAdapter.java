package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.Announcement;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.UpdateViewHolder> {

    private List<Announcement> announcements;

    public UpdatesAdapter(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @NonNull
    @Override
    public UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_updates, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        holder.bind(announcement);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
        notifyDataSetChanged();
    }

    static class UpdateViewHolder extends RecyclerView.ViewHolder {
        private ImageView updateTypeIcon;
        private TextView updateTitle;
        private TextView updateDescription;
        private TextView updateCategory;
        private TextView updateDate;
        private MaterialCardView cardView;

        UpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            updateTypeIcon = itemView.findViewById(R.id.updateTypeIcon);
            updateTitle = itemView.findViewById(R.id.updateTitle);
            updateDescription = itemView.findViewById(R.id.updateDescription);
            updateCategory = itemView.findViewById(R.id.updateCategory);
            updateDate = itemView.findViewById(R.id.updateDate);
            cardView = (MaterialCardView) itemView;
        }

        void bind(Announcement announcement) {
            updateTitle.setText(announcement.getTitle());
            updateDescription.setText(announcement.getDescription());
            updateCategory.setText(announcement.getCategory());
            updateDate.setText(announcement.getDate());

            // Set the icon based on the announcement category
            int iconResId;
            switch (announcement.getCategory().toLowerCase()) {
                case "event updates":
                    iconResId = R.drawable.ic_event;
                    break;
                case "academic achievements":
                    iconResId = R.drawable.ic_academic;
                    break;
                case "weather advisory":
                    iconResId = R.drawable.ic_weather;
                    break;
                case "campus news":
                    iconResId = R.drawable.ic_news;
                    break;
                default:
                    iconResId = R.drawable.ic_home;
                    break;
            }
            updateTypeIcon.setImageResource(iconResId);
        }
    }
}