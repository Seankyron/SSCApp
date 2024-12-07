package com.example.sscapp.admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminCALagapayActivity extends AppCompatActivity {

    private RecyclerView requestsRecyclerView;
    private CalculatorRequestAdapter requestAdapter;
    private List<CalculatorRequest> allRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_calagapay);

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView);
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestAdapter = new CalculatorRequestAdapter();
        requestsRecyclerView.setAdapter(requestAdapter);

        allRequests = new ArrayList<>();
        fetchCalagapayEntries();
    }

    private void fetchCalagapayEntries() {
        new FetchCalagapayEntriesTask().execute("http://192.168.1.5:5000/api/admin/calagapay");
    }

    private class FetchCalagapayEntriesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String apiUrl = urls[0];
            HttpURLConnection connection = null;
            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(AdminCALagapayActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                allRequests.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CalculatorRequest request = new CalculatorRequest(
                            jsonObject.getString("srCode"),
                            jsonObject.getString("name"),
                            jsonObject.getString("program"),
                            jsonObject.getString("calculatorNumber"),
                            jsonObject.getString("purposeOfBorrowing"),
                            jsonObject.getString("dateOfBorrowing")
                    );
                    allRequests.add(request);
                }
                requestAdapter.setRequests(allRequests);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(AdminCALagapayActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class CalculatorRequest {
        String srCode, name, program, calculatorNumber, purposeOfBorrowing, dateOfBorrowing;

        CalculatorRequest(String srCode, String name, String program, String calculatorNumber, String purposeOfBorrowing, String dateOfBorrowing) {
            this.srCode = srCode;
            this.name = name;
            this.program = program;
            this.calculatorNumber = calculatorNumber;
            this.purposeOfBorrowing = purposeOfBorrowing;
            this.dateOfBorrowing = dateOfBorrowing;
        }
    }

    private class CalculatorRequestAdapter extends RecyclerView.Adapter<CalculatorRequestAdapter.RequestViewHolder> {
        private List<CalculatorRequest> requests = new ArrayList<>();

        @NonNull
        @Override
        public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculator_request, parent, false);
            return new RequestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
            CalculatorRequest request = requests.get(position);
            holder.bind(request);
        }

        @Override
        public int getItemCount() {
            return requests.size();
        }

        void setRequests(List<CalculatorRequest> requests) {
            this.requests = requests;
            notifyDataSetChanged();
        }

        class RequestViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView, calculatorNumberTextView, purposeTextView, dateTextView;

            RequestViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.studentNameTextView);
                calculatorNumberTextView = itemView.findViewById(R.id.calculatorNumberTextView);
                purposeTextView = itemView.findViewById(R.id.purposeTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);
            }

            void bind(final CalculatorRequest request) {
                nameTextView.setText(request.name);
                calculatorNumberTextView.setText(request.calculatorNumber);
                purposeTextView.setText(request.purposeOfBorrowing);
                dateTextView.setText(request.dateOfBorrowing);
            }
        }
    }
}
