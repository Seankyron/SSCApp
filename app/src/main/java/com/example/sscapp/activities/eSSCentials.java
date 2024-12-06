package com.example.sscapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.sscapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class eSSCentials extends AppCompatActivity {

    private CheckBox checkBoxWalkieTalkie, checkBoxSpeaker, checkBoxMicrophone;
    private EditText editTextWalkieTalkieCount, editTextMicrophoneCount, editTextPurpose, editTextDate;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esscentials);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        setupListeners();
    }

    private void initViews() {
        checkBoxWalkieTalkie = findViewById(R.id.checkBoxWalkieTalkie);
        checkBoxSpeaker = findViewById(R.id.checkBoxSpeaker);
        checkBoxMicrophone = findViewById(R.id.checkBoxMicrophone);

        editTextWalkieTalkieCount = findViewById(R.id.editTextWalkieTalkieCount);
        editTextMicrophoneCount = findViewById(R.id.editTextMicrophoneCount);
        editTextPurpose = findViewById(R.id.editTextPurpose);
        editTextDate = findViewById(R.id.editTextDate);

        buttonSubmit = findViewById(R.id.buttonSubmit);
    }

    private void setupListeners() {
        editTextWalkieTalkieCount.addTextChangedListener(new ValidationTextWatcher(editTextWalkieTalkieCount));
        editTextMicrophoneCount.addTextChangedListener(new ValidationTextWatcher(editTextMicrophoneCount));

        editTextDate.setOnClickListener(v -> showDatePickerDialog());
        buttonSubmit.setOnClickListener(v -> submitRequest());
    }

    private void showDatePickerDialog() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            editTextDate.setText(sdf.format(new Date(selection)));
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void submitRequest() {
        if (!validateInputs()) {
            return;
        }

        // Replace this with your actual API endpoint
        String apiUrl = "http://192.168.1.5:5000/api/admin/esscential";

        // Default values for user details (these would typically come from a session or local storage)
        String srcode = "22-01122";
        String name = "Mico Raphael F. Cuarto";
        String program = "BSCS";

        // Retrieve form inputs
        String walkieTalkieCount = editTextWalkieTalkieCount.getText().toString();
        String microphoneCount = editTextMicrophoneCount.getText().toString();
        String purpose = editTextPurpose.getText().toString();
        String date = editTextDate.getText().toString();

        // Compile the devices needed string
        StringBuilder devicesNeeded = new StringBuilder();

        if (checkBoxWalkieTalkie.isChecked()) {
            devicesNeeded.append("Walkie Talkie: ").append(walkieTalkieCount).append(" units, ");
        }
        if (checkBoxSpeaker.isChecked()) {
            devicesNeeded.append("Speaker, ");
        }
        if (checkBoxMicrophone.isChecked()) {
            devicesNeeded.append("Microphone: ").append(microphoneCount).append(" units, ");
        }

        // Remove the trailing comma and space if there is any
        if (devicesNeeded.length() > 0) {
            devicesNeeded.setLength(devicesNeeded.length() - 2);
        }

        // Create the JSON payload
        String requestBody = String.format(
                "{\"srCode\":\"%s\",\"name\":\"%s\",\"program\":\"%s\",\"deviceNeeded\":\"%s\",\"purpose\":\"%s\",\"date\":\"%s\"}",
                srcode, name, program, devicesNeeded.toString(), purpose, date
        );

        // Execute the AsyncTask to send the request
        new SubmitRequestTask().execute(apiUrl, requestBody);
        Toast.makeText(this, "Request sent.", Toast.LENGTH_SHORT).show();

        // Clear all form fields after submission
        editTextWalkieTalkieCount.setText("");
        editTextMicrophoneCount.setText("");
        editTextPurpose.setText("");
        editTextDate.setText("");
    }


    private static class SubmitRequestTask extends AsyncTask<String, Void, String> {
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


    private boolean validateInputs() {
        boolean isValid = true;

        if (!checkBoxWalkieTalkie.isChecked() && !checkBoxSpeaker.isChecked() && !checkBoxMicrophone.isChecked()) {
            showToast("Please select at least one item to borrow");
            isValid = false;
        }

        if (checkBoxWalkieTalkie.isChecked()) {
            String count = editTextWalkieTalkieCount.getText().toString();
            if (count.isEmpty() || Integer.parseInt(count) == 0) {
                editTextWalkieTalkieCount.setError("Please enter a valid quantity");
                isValid = false;
            }
        }

        if (checkBoxMicrophone.isChecked()) {
            String count = editTextMicrophoneCount.getText().toString();
            if (count.isEmpty() || Integer.parseInt(count) == 0) {
                editTextMicrophoneCount.setError("Please enter a valid quantity");
                isValid = false;
            }
        }

        if (editTextPurpose.getText().toString().trim().isEmpty()) {
            editTextPurpose.setError("Please enter the purpose of borrowing");
            isValid = false;
        }

        if (editTextDate.getText().toString().trim().isEmpty()) {
            editTextDate.setError("Please select a date");
            isValid = false;
        }

        return isValid;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class ValidationTextWatcher implements TextWatcher {
        private EditText editText;

        public ValidationTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            validateCountField(editText);
        }

        private void validateCountField(EditText editText) {
            try {
                int count = Integer.parseInt(editText.getText().toString());
                if (count < 0) {
                    editText.setError("Please enter a valid quantity");
                } else {
                    editText.setError(null);
                }
            } catch (NumberFormatException e) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError(null);
                } else {
                    editText.setError("Please enter a valid quantity");
                }
            }
        }
    }
}
