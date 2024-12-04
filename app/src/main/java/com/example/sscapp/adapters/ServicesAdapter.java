package com.example.sscapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.Service;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final List<Service> services;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Service service);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ServicesAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = services.get(position);
        holder.icon.setImageResource(service.getIconResId());
        holder.title.setText(service.getTitle());
        holder.description.setText(service.getDescription());
        holder.status.setText(service.getStatus());

        int backgroundColor;
        switch (service.getStatus().toLowerCase()) {
            case "available":
                backgroundColor = ContextCompat.getColor(context, R.color.green);
                break;
            case "limited":
                backgroundColor = ContextCompat.getColor(context, R.color.yellow_dark);
                break;
            case "not available":
                backgroundColor = ContextCompat.getColor(context, R.color.red_primary);
                break;
            default:
                backgroundColor = ContextCompat.getColor(context, R.color.gray_dark);
                break;
        }

        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.bg_service_status);
        if (drawable != null) {
            drawable.setColor(backgroundColor);
            holder.status.setBackground(drawable);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, description, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.serviceIcon);
            title = itemView.findViewById(R.id.serviceTitle);
            description = itemView.findViewById(R.id.serviceDescription);
            status = itemView.findViewById(R.id.serviceStatus);
        }
    }
}

