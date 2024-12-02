package com.example.sscapp.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.models.Officer;

import java.util.List;

public class OfficerAdapter extends RecyclerView.Adapter<OfficerAdapter.OfficerViewHolder> {
    private List<Officer> officers;

    public OfficerAdapter(List<Officer> officers) {
        this.officers = officers;
    }

    @NonNull
    @Override
    public OfficerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_officer, parent, false);
        return new OfficerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficerViewHolder holder, int position) {
        Officer officer = officers.get(position);
        holder.nameText.setText(officer.getName());
        holder.positionText.setText(officer.getPosition());
        holder.detailsText.setText(officer.getDetails());
    }

    @Override
    public int getItemCount() {
        return officers.size();
    }

    static class OfficerViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, positionText, detailsText;

        public OfficerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.officerNameText);
            positionText = itemView.findViewById(R.id.officerPositionText);
            detailsText = itemView.findViewById(R.id.officerDetailsText);
        }
    }
}
