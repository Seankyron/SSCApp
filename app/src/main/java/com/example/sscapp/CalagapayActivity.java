package com.example.sscapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class CalagapayActivity extends AppCompatActivity {

    private TextInputEditText purposeEditText;
    private TextInputEditText dateOfClaimingEditText;
    private MaterialButton submitCalculatorRequestButton;

    private final String[] calculators = {"Calc-1", "Calc-2", "Calc-3", "Calc-4", "Calc-5", "Calc-6", "Calc-7", "Calc-8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calagapay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        purposeEditText = findViewById(R.id.purposeEditText);
        dateOfClaimingEditText = findViewById(R.id.dateOfClaimingEditText);
        submitCalculatorRequestButton = findViewById(R.id.submitCalculatorRequestButton);
    }

    private void setupListeners() {
        dateOfClaimingEditText.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                dateOfClaimingEditText.setText(sdf.format(new Date(selection)));
            });
            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        submitCalculatorRequestButton.setOnClickListener(v -> {
            Toast.makeText(this, "Submit button pressed", Toast.LENGTH_SHORT).show();
            submitCalculatorRequest();
        });

    }

    private void submitCalculatorRequest() {
        // Validate inputs
        if (purposeEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter the purpose of borrowing", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dateOfClaimingEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a date of claiming", Toast.LENGTH_SHORT).show();
            return;
        }

        // User details
        String srcode = "22-01122";
        String name = "Mico Raphael F. Cuarto";
        String program = "BSCS";

        // Form inputs
        String purpose = purposeEditText.getText().toString();
        String dateOfClaiming = dateOfClaimingEditText.getText().toString();

        // Randomly select a calculator
        Random random = new Random();
        String selectedCalculator = calculators[random.nextInt(calculators.length)];

        // Create JSON payload
        String requestBody = String.format(
                "{\"srCode\":\"%s\",\"name\":\"%s\",\"program\":\"%s\","
                        + "\"calculatorNumber\":\"%s\",\"purposeOfBorrowing\":\"%s\","
                        + "\"dateOfBorrowing\":\"%s\"}",
                srcode, name, program, selectedCalculator, purpose, dateOfClaiming
        );

        // API endpoint
        String apiUrl = "http://192.168.1.5:5000/api/admin/calagapay";

        // Send the request
        new SubmitRequestTask().execute(apiUrl, requestBody);
        Toast.makeText(this, "Request sent.", Toast.LENGTH_SHORT).show();
        purposeEditText.setText("");
        dateOfClaimingEditText.setText("");
    }

    public static class SubmitRequestTask extends AsyncTask<String, Void, String> {
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

                // Write the request body
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody);
                writer.flush();
                writer.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Request submitted successfully.";
                } else {
                    return "Failed to submit request. Response code: " + responseCode;
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
