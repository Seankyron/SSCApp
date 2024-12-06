package com.example.sscapp.admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.adapters.PaymentAdapter;
import com.example.sscapp.models.Payment;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminMembershipActivity extends AppCompatActivity implements PaymentAdapter.OnPaymentVerificationListener {
    private RecyclerView paymentListRecyclerView;
    private TextInputEditText searchEditText;
    private TextView totalPaymentsTextView;
    private PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_membership);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        paymentListRecyclerView = findViewById(R.id.paymentListRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        totalPaymentsTextView = findViewById(R.id.totalPaymentsTextView);

        setupRecyclerView();
        setupSearch();

        // Load sample data (replace this with actual data loading logic)
        loadSampleData();
    }

    private void setupRecyclerView() {
        paymentAdapter = new PaymentAdapter(this);
        paymentListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentListRecyclerView.setAdapter(paymentAdapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                paymentAdapter.filter(s.toString());
                updateTotalPayments();
            }
        });
    }

    private void updateTotalPayments() {
        double total = paymentAdapter.getTotalPayments();
        totalPaymentsTextView.setText(String.format(Locale.getDefault(), "Total Payments: ₱%.2f", total));
    }

    private void loadSampleData() {
        List<Payment> samplePayments = new ArrayList<>();
        samplePayments.add(new Payment("John Doe", "REF001", 45.0, new Date(), "Pending"));
        samplePayments.add(new Payment("Jane Smith", "REF002", 45.0, new Date(), "Pending"));
        samplePayments.add(new Payment("Bob Johnson", "REF003", 45.0, new Date(), "Verified"));
        paymentAdapter.setPayments(samplePayments);
        updateTotalPayments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentVerified(Payment payment) {
        // Update the payment status
        payment.setStatus("Verified");

        // Update the database or send to server
        // For this example, we'll just update the adapter
        paymentAdapter.notifyDataSetChanged();

        // Show a confirmation message
        Toast.makeText(this, "Payment verified: " + payment.getReferenceNumber(), Toast.LENGTH_SHORT).show();

        // Update the user's view (you would typically do this through a database or server)
        updateUserView(payment);
    }

    private void updateUserView(Payment payment) {
        // In a real application, you would update the user's view through a database or server
        // For this example, we'll just simulate the update
        // You might want to use a shared preferences or database to store the verification status
        // so it persists across app restarts

        // Example of how you might update a shared preference:
        getSharedPreferences("UserPayments", MODE_PRIVATE)
                .edit()
                .putString(payment.getReferenceNumber() + "_status", "Verified")
                .apply();

        // In the user's MembershipPaymentActivity, you would check this preference when displaying the status
    }
}

