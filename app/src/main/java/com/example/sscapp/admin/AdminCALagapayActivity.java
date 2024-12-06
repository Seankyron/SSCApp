package com.example.sscapp.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.example.sscapp.adapters.CalculatorRequestAdapter;
import com.example.sscapp.models.CalculatorRequest;

import java.util.ArrayList;
import java.util.List;

public class AdminCALagapayActivity extends AppCompatActivity implements CalculatorRequestAdapter.OnCalculatorReturnedListener {

    private RecyclerView requestsRecyclerView;
    private CalculatorRequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_calagapay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView);
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Implement database attachment to fetch real data
        List<CalculatorRequest> requests = getDummyRequests();

        adapter = new CalculatorRequestAdapter(requests, this);
        requestsRecyclerView.setAdapter(adapter);
    }

    private List<CalculatorRequest> getDummyRequests() {
        List<CalculatorRequest> requests = new ArrayList<>();
        requests.add(new CalculatorRequest("John Doe", "Calculator 1", "Exam use", "2023-06-15"));
        requests.add(new CalculatorRequest("Jane Smith", "Calculator 3", "Coursework", "2023-06-16"));
        // Add more dummy data as needed
        return requests;
    }

    @Override
    public void onCalculatorReturned(CalculatorRequest request) {
        // TODO: Implement database attachment
        // databaseAttachment.markCalculatorAsReturned(request.getCalculatorNumber());

        // Update the UI
        adapter.removeRequest(request);

        // TODO: Update the user view
        // Send a broadcast or use a shared ViewModel to update the CALagapay activity
        Toast.makeText(this, "Calculator " + request.getCalculatorNumber() + " marked as returned", Toast.LENGTH_SHORT).show();
    }
}

