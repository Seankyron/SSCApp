package com.example.sscapp.admin;

import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

        allRequests = createDummyData();
        filterRequests(null); // Initially show all requests
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

    private void filterRequests(String filterDate) {
        List<PrintRequest> filteredRequests;
        if (filterDate == null || filterDate.isEmpty()) {
            filteredRequests = allRequests;
        } else {
            filteredRequests = allRequests.stream()
                    .filter(request -> request.dateOfClaiming.equals(filterDate))
                    .collect(Collectors.toList());
        }
        requestAdapter.setRequests(filteredRequests);
    }

    private List<PrintRequest> createDummyData() {
        List<PrintRequest> dummyRequests = new ArrayList<>();
        dummyRequests.add(new PrintRequest("22-01122", "Mico Raphael F. Cuarto", "BSCS", "thesis_draft.pdf", "A4", 2, "May 15, 2024", "Please print double-sided"));
        dummyRequests.add(new PrintRequest("22-02233", "Maria Santos", "BSIT", "project_proposal.docx", "Letter", 1, "May 16, 2024", "Color printing required"));
        dummyRequests.add(new PrintRequest("22-03344", "John Doe", "BSECE", "circuit_diagram.png", "A3", 3, "May 17, 2024", "High quality print needed"));
        dummyRequests.add(new PrintRequest("22-04455", "Emily Johnson", "BSA", "financial_report.xlsx", "Legal", 5, "May 18, 2024", "Confidential document"));
        dummyRequests.add(new PrintRequest("22-05566", "Michael Brown", "BSME", "machine_blueprint.pdf", "A2", 1, "May 19, 2024", "Special paper required"));
        return dummyRequests;
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

                markAsDoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request.isDone = true;
                        markAsDoneButton.setEnabled(false);
                        markAsDoneButton.setText("Done");
                        Toast.makeText(itemView.getContext(), "Marked as done: " + request.name, Toast.LENGTH_SHORT).show();
                    }
                });

                markAsDoneButton.setEnabled(!request.isDone);
                markAsDoneButton.setText(request.isDone ? "Done" : "Mark as Done");
            }
        }
    }
}

