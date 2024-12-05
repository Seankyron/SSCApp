package com.example.sscapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProjectAgapayActivity extends AppCompatActivity {

    private TextView fileNameTextView;
    private Button uploadFileButton;
    private AutoCompleteTextView paperSizeDropdown;
    private EditText numberOfCopiesEditText;
    private TextInputEditText dateOfClaimingEditText;
    private EditText remarksEditText;
    private Button submitPrintRequestButton;

    private Uri selectedFileUri;

    private final ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedFileUri = uri;
                    String fileName = selectedFileUri.getLastPathSegment();
                    fileNameTextView.setText(fileName);
                    fileNameTextView.setVisibility(View.VISIBLE);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_agapay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        fileNameTextView = findViewById(R.id.fileNameTextView);
        uploadFileButton = findViewById(R.id.uploadFileButton);
        paperSizeDropdown = findViewById(R.id.paperSizeDropdown);
        numberOfCopiesEditText = findViewById(R.id.numberOfCopiesEditText);
        dateOfClaimingEditText = findViewById(R.id.dateOfClaimingEditText);
        remarksEditText = findViewById(R.id.remarksEditText);
        submitPrintRequestButton = findViewById(R.id.submitPrintRequestButton);

        String[] paperSizes = {"A4", "Letter", "Legal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, paperSizes);
        paperSizeDropdown.setAdapter(adapter);
    }

    private void setupListeners() {
        uploadFileButton.setOnClickListener(v -> filePickerLauncher.launch("*/*"));

        dateOfClaimingEditText.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                dateOfClaimingEditText.setText(sdf.format(new Date(selection)));
            });
            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        submitPrintRequestButton.setOnClickListener(v -> submitPrintRequest());
    }

    private void submitPrintRequest() {
        if (validateForm()) {
            String apiUrl = "http://192.168.1.5:5000/api/admin/projectagapay";

            // Default values for sr code, name, and program
            String srcode = "22-01122";
            String name = "Mico Raphael F. Cuarto";
            String program = "BSCS";

            // Retrieve other form inputs
            String paperSize = paperSizeDropdown.getText().toString();
            String numberOfCopies = numberOfCopiesEditText.getText().toString();
            String dateOfClaiming = dateOfClaimingEditText.getText().toString();
            String remarks = remarksEditText.getText().toString();

            // Create the JSON payload
            String requestBody = String.format(
                    "{\"srcode\":\"%s\",\"name\":\"%s\",\"program\":\"%s\",\"fileName\":\"%s\",\"paperSize\":\"%s\",\"numberOfCopies\":%s,\"dateOfClaiming\":\"%s\",\"remarks\":\"%s\"}",
                    srcode, name, program, fileNameTextView.getText().toString(), paperSize, numberOfCopies, dateOfClaiming, remarks
            );

            // Execute the AsyncTask to send the request
            new SubmitPrintRequestTask().execute(apiUrl, requestBody);
        }
    }


    private boolean validateForm() {
        if (selectedFileUri == null) {
            Toast.makeText(this, "Please upload a file", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (paperSizeDropdown.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a paper size", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (numberOfCopiesEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter the number of copies", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateOfClaimingEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a claiming date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // AsyncTask to handle the API request
    private static class SubmitPrintRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String apiUrl = params[0];
            String requestBody = params[1];

            HttpURLConnection connection = null;

            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Send the request body
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody);
                writer.flush();
                writer.close();

                // Get the response code
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Request submitted successfully.";
                } else {
                    return "Failed to submit the request. Response code: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}
