package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.Payment;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<Payment> payments;
    private List<Payment> filteredPayments;
    private OnPaymentVerificationListener verificationListener;

    public interface OnPaymentVerificationListener {
        void onPaymentVerified(Payment payment);
    }

    public PaymentAdapter(OnPaymentVerificationListener listener) {
        payments = new ArrayList<>();
        filteredPayments = new ArrayList<>();
        this.verificationListener = listener;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = filteredPayments.get(position);
        holder.bind(payment);
    }

    @Override
    public int getItemCount() {
        return filteredPayments.size();
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        this.filteredPayments = new ArrayList<>(payments);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredPayments.clear();
        if (query.isEmpty()) {
            filteredPayments.addAll(payments);
        } else {
            query = query.toLowerCase();
            for (Payment payment : payments) {
                if (payment.getStudentName().toLowerCase().contains(query) ||
                        payment.getReferenceNumber().toLowerCase().contains(query)) {
                    filteredPayments.add(payment);
                }
            }
        }
        notifyDataSetChanged();
    }

    public double getTotalPayments() {
        double total = 0;
        for (Payment payment : filteredPayments) {
            total += payment.getAmount();
        }
        return total;
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView studentNameTextView;
        private TextView referenceNumberTextView;
        private TextView amountTextView;
        private TextView dateTextView;
        private TextView statusTextView;
        private MaterialButton verifyButton;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            referenceNumberTextView = itemView.findViewById(R.id.referenceNumberTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            verifyButton = itemView.findViewById(R.id.verifyButton);
        }

        public void bind(Payment payment) {
            studentNameTextView.setText(payment.getStudentName());
            referenceNumberTextView.setText(payment.getReferenceNumber());
            amountTextView.setText(String.format(Locale.getDefault(), "₱%.2f", payment.getAmount()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            dateTextView.setText(dateFormat.format(payment.getDate()));
            statusTextView.setText(payment.getStatus());

            if ("Verified".equals(payment.getStatus())) {
                verifyButton.setVisibility(View.GONE);
            } else {
                verifyButton.setVisibility(View.VISIBLE);
                verifyButton.setOnClickListener(v -> {
                    if (verificationListener != null) {
                        verificationListener.onPaymentVerified(payment);
                    }
                });
            }
        }
    }
}

