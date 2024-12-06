package com.example.sscapp.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.bumptech.glide.Glide;
import com.example.sscapp.models.Announcement;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolder> {

    private final List<Announcement> announcements;
    private int itemCount = 0;
    private Context context;

    public AnnouncementsAdapter(Context context, List<Announcement> announcements) {
        this.announcements = announcements;
        this.context = context;
    }

    public void setItemCount(int count) {
        this.itemCount = count;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Announcement announcement = announcements.get(position % announcements.size());
        holder.title.setText(announcement.getTitle());
        holder.description.setText(announcement.getDescription());
        holder.type.setText(announcement.getType().toUpperCase());
        holder.category.setText(announcement.getCategory());

        // Set type badge background color based on announcement type
        int backgroundColor;
        int textColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.white);

        switch (announcement.getType().toLowerCase()) {
            case "urgent":
                backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.red_primary);
                break;
            case "important":
                backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.yellow_dark);
                textColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.black); // Better contrast for yellow
                break;
            default:
                backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
                break;
        }

        holder.type.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        holder.type.setTextColor(textColor);

        // Set title color based on type
        if (announcement.getType().toLowerCase().equals("urgent")) {
            holder.title.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red_primary));
        } else {
            holder.title.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_primary));
        }

        // Set category and author text colors
        holder.category.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_secondary));



        Glide.with(context)
                .load(announcement.getImage())
                .fitCenter()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemCount == 0 ? announcements.size() : itemCount;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, type, category, author;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.announcementTitle);
            description = itemView.findViewById(R.id.announcementDescription);
            type = itemView.findViewById(R.id.announcementType);
            category = itemView.findViewById(R.id.announcementCategory);
            image = itemView.findViewById(R.id.announcementImage);
        }
    }
}

