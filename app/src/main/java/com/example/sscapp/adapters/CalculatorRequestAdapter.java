package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.models.CalculatorRequest;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CalculatorRequestAdapter extends RecyclerView.Adapter<CalculatorRequestAdapter.ViewHolder> {

    private List<CalculatorRequest> requests;
    private OnCalculatorReturnedListener listener;

    public interface OnCalculatorReturnedListener {
        void onCalculatorReturned(CalculatorRequest request);
    }

    public CalculatorRequestAdapter(List<CalculatorRequest> requests, OnCalculatorReturnedListener listener) {
        this.requests = requests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculator_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalculatorRequest request = requests.get(position);
        holder.studentNameTextView.setText(request.getStudentName());
        holder.calculatorNumberTextView.setText(request.getCalculatorNumber());
        holder.purposeTextView.setText(request.getPurpose());
        holder.dateTextView.setText(request.getDate());

        holder.markAsReturnedButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCalculatorReturned(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void removeRequest(CalculatorRequest request) {
        int position = requests.indexOf(request);
        if (position != -1) {
            requests.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        TextView calculatorNumberTextView;
        TextView purposeTextView;
        TextView dateTextView;
        MaterialButton markAsReturnedButton;

        ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            calculatorNumberTextView = itemView.findViewById(R.id.calculatorNumberTextView);
            purposeTextView = itemView.findViewById(R.id.purposeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            markAsReturnedButton = itemView.findViewById(R.id.markAsReturnedButton);
        }
    }
}

