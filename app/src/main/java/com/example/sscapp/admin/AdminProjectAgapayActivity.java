package com.example.sscapp.admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminProjectAgapayActivity extends AppCompatActivity {

    private RecyclerView requestsRecyclerView;
    private RequestAdapter requestAdapter;
    private TextInputEditText dateFilterEditText;
    private List<PrintRequest> allRequests;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_project_agapay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateFilterEditText = findViewById(R.id.dateFilterEditText);
        setupDateFilter();

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView);
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestAdapter = new RequestAdapter();
        requestsRecyclerView.setAdapter(requestAdapter);

        allRequests = new ArrayList<>();
        fetchAgapayEntries();
    }

    private void setupDateFilter() {
        dateFilterEditText.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                String formattedDate = dateFormat.format(new Date(selection));
                dateFilterEditText.setText(formattedDate);
                filterRequests(formattedDate);
            });

            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });
    }

    private void fetchAgapayEntries() {
        new FetchAgapayEntriesTask().execute("http://192.168.1.5:5000/api/admin/projectagapay");
    }

    private void filterRequests(String filterDate) {
        List<PrintRequest> filteredRequests;
        if (filterDate == null || filterDate.isEmpty()) {
            filteredRequests = allRequests;
        } else {
            filteredRequests = new ArrayList<>();
            for (PrintRequest request : allRequests) {
                if (request.dateOfClaiming.equals(filterDate)) {
                    filteredRequests.add(request);
                }
            }
        }
        requestAdapter.setRequests(filteredRequests);
    }

    private class FetchAgapayEntriesTask extends AsyncTask<String, Void, String> {
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
                Toast.makeText(AdminProjectAgapayActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                allRequests.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    PrintRequest request = new PrintRequest(
                            jsonObject.getString("srcode"),
                            jsonObject.getString("name"),
                            jsonObject.getString("program"),
                            jsonObject.getString("fileName"),
                            jsonObject.getString("paperSize"),
                            jsonObject.getInt("numberOfCopies"),
                            jsonObject.getString("dateOfClaiming"),
                            jsonObject.getString("remarks")
                    );
                    allRequests.add(request);
                }
                filterRequests(null); // Update UI with fetched data
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(AdminProjectAgapayActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class PrintRequest {
        String srcode, name, program, fileName, paperSize, dateOfClaiming, remarks;
        int numberOfCopies;
        boolean isDone;

        PrintRequest(String srcode, String name, String program, String fileName, String paperSize, int numberOfCopies, String dateOfClaiming, String remarks) {
            this.srcode = srcode;
            this.name = name;
            this.program = program;
            this.fileName = fileName;
            this.paperSize = paperSize;
            this.numberOfCopies = numberOfCopies;
            this.dateOfClaiming = dateOfClaiming;
            this.remarks = remarks;
            this.isDone = false;
        }
    }

    private class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
        private List<PrintRequest> requests = new ArrayList<>();

        @NonNull
        @Override
        public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_request, parent, false);
            return new RequestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
            PrintRequest request = requests.get(position);
            holder.bind(request);
        }

        @Override
        public int getItemCount() {
            return requests.size();
        }

        void setRequests(List<PrintRequest> requests) {
            this.requests = requests;
            notifyDataSetChanged();
        }

        class RequestViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView, srcodeTextView, programTextView, fileNameTextView, paperSizeTextView, copiesTextView, dateTextView, remarksTextView;
            MaterialButton markAsDoneButton;

            RequestViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                srcodeTextView = itemView.findViewById(R.id.srcodeTextView);
                programTextView = itemView.findViewById(R.id.programTextView);
                fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
                paperSizeTextView = itemView.findViewById(R.id.paperSizeTextView);
                copiesTextView = itemView.findViewById(R.id.copiesTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);
                remarksTextView = itemView.findViewById(R.id.remarksTextView);
                markAsDoneButton = itemView.findViewById(R.id.markAsDoneButton);
            }

            void bind(final PrintRequest request) {
                nameTextView.setText(request.name);
                srcodeTextView.setText(request.srcode);
                programTextView.setText(request.program);
                fileNameTextView.setText(request.fileName);
                paperSizeTextView.setText(request.paperSize);
                copiesTextView.setText(String.valueOf(request.numberOfCopies));
                dateTextView.setText(request.dateOfClaiming);
                remarksTextView.setText(request.remarks);

                markAsDoneButton.setOnClickListener(v -> {
                    request.isDone = true;
                    markAsDoneButton.setEnabled(false);
                    markAsDoneButton.setText("Done");
                    Toast.makeText(itemView.getContext(), "Marked as done: " + request.name, Toast.LENGTH_SHORT).show();
                });

                markAsDoneButton.setEnabled(!request.isDone);
                markAsDoneButton.setText(request.isDone ? "Done" : "Mark as Done");
            }
        }
    }
}
