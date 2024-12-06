package com.example.sscapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.models.ServiceUsage;

import java.util.List;

public class ServiceUsageAdapter extends RecyclerView.Adapter<ServiceUsageAdapter.ServiceViewHolder> {

    private Context context;
    private List<ServiceUsage> serviceUsages;

    public ServiceUsageAdapter(Context context, List<ServiceUsage> serviceUsages) {
        this.context = context;
        this.serviceUsages = serviceUsages;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_usage_card, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceUsage service = serviceUsages.get(position);

        holder.serviceIcon.setImageResource(service.getIconResourceId());
        holder.serviceName.setText(service.getName());
        holder.serviceUsage.setText(service.getUsageCount() + " uses");
    }

    @Override
    public int getItemCount() {
        return serviceUsages.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceIcon;
        TextView serviceName;
        TextView serviceUsage;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceIcon = itemView.findViewById(R.id.service_icon);
            serviceName = itemView.findViewById(R.id.service_name);
            serviceUsage = itemView.findViewById(R.id.service_usage);
        }
    }
}