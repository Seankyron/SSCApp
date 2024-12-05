package com.example.sscapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterName;
    private EditText editTextRegisterContact;
    private EditText editTextRegisterGsuite;
    private EditText editTextRegisterSrCode;
    private EditText editTextRegisterDepartment;
    private EditText editTextRegisterProgram;
    private EditText editTextRegisterYear;
    private EditText editTextRegisterPassword;
    private EditText editTextRegisterConfirmPassword;
    private Button buttonRegister;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the views
        editTextRegisterName = findViewById(R.id.editText_register_name);
        editTextRegisterContact = findViewById(R.id.editText_register_contact);
        editTextRegisterGsuite = findViewById(R.id.editText_register_gsuite);
        editTextRegisterSrCode = findViewById(R.id.editText_register_sr_code);
        editTextRegisterDepartment = findViewById(R.id.editText_register_department);
        editTextRegisterProgram = findViewById(R.id.editText_register_program);
        editTextRegisterYear = findViewById(R.id.editText_register_year);
        editTextRegisterPassword = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPassword = findViewById(R.id.editText_register_confirm_password);
        buttonRegister = findViewById(R.id.button_register);
        loginLink = findViewById(R.id.textView_login);

        buttonRegister.setOnClickListener(v -> registerUser());
        loginLink.setOnClickListener(v -> finish()); // This will go back to LoginActivity

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser() {
        if (validateForm()) {
            String apiUrl = "http://192.168.1.5:5000/api/auth/register";

            // Collect form data
            String name = editTextRegisterName.getText().toString();
            String contactNumber = editTextRegisterContact.getText().toString();
            String email = editTextRegisterGsuite.getText().toString();
            String srCode = editTextRegisterSrCode.getText().toString();
            String department = editTextRegisterDepartment.getText().toString();
            String program = editTextRegisterProgram.getText().toString();
            String year = editTextRegisterYear.getText().toString();
            String password = editTextRegisterPassword.getText().toString();

            // Create the JSON payload
            String requestBody = String.format(
                    "{\"name\":\"%s\",\"contactNumber\":\"%s\",\"email\":\"%s\",\"srCode\":\"%s\",\"departmentName\":\"%s\",\"program\":\"%s\",\"yearLevel\":\"%s\",\"password\":\"%s\"}",
                    name, contactNumber, email, srCode, department, program, year, password
            );

            // Execute the AsyncTask to send the request
            new RegisterUserTask().execute(apiUrl, requestBody);
            showToast("User Registered Successfully!");
            clearForm();
        }
    }

    private boolean validateForm() {
        if (editTextRegisterName.getText().toString().isEmpty()) {
            showToast("Please enter your name");
            return false;
        }
        if (editTextRegisterContact.getText().toString().isEmpty()) {
            showToast("Please enter your contact number");
            return false;
        }
        if (editTextRegisterGsuite.getText().toString().isEmpty()) {
            showToast("Please enter your Gsuite account");
            return false;
        }
        if (editTextRegisterSrCode.getText().toString().isEmpty()) {
            showToast("Please enter your SR Code");
            return false;
        }
        if (editTextRegisterDepartment.getText().toString().isEmpty()) {
            showToast("Please enter your department");
            return false;
        }
        if (editTextRegisterProgram.getText().toString().isEmpty()) {
            showToast("Please enter your program");
            return false;
        }
        if (editTextRegisterYear.getText().toString().isEmpty()) {
            showToast("Please enter your year level");
            return false;
        }
        if (editTextRegisterPassword.getText().toString().isEmpty()) {
            showToast("Please enter your password");
            return false;
        }
        if (!editTextRegisterPassword.getText().toString().equals(editTextRegisterConfirmPassword.getText().toString())) {
            showToast("Passwords do not match");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearForm() {
        editTextRegisterName.setText("");
        editTextRegisterContact.setText("");
        editTextRegisterGsuite.setText("");
        editTextRegisterSrCode.setText("");
        editTextRegisterDepartment.setText("");
        editTextRegisterProgram.setText("");
        editTextRegisterYear.setText("");
        editTextRegisterPassword.setText("");
        editTextRegisterConfirmPassword.setText("");
    }

    // AsyncTask to handle the API request for registration
    private class RegisterUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String apiUrl = params[0];
            String requestBody = params[1];
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Send the request body
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(requestBody);
                    writer.flush();
                }

                // Get the response
                int responseCode = connection.getResponseCode();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Success: " + response.toString();
                } else {
                    return "Error: " + response.toString();
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

