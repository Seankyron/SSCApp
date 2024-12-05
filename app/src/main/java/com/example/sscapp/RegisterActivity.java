package com.example.sscapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

        buttonRegister.setOnClickListener(v -> registerUser());
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
                    "{\"name\":\"%s\",\"contactNumber\":\"%s\",\"email\":\"%s\",\"srCode\":\"%s\",\"department\":\"%s\",\"program\":\"%s\",\"year\":\"%s\",\"password\":\"%s\"}",
                    name, contactNumber, email, srCode, department, program, year, password
            );

            // Execute the AsyncTask to send the request
            new RegisterUserTask().execute(apiUrl, requestBody);

            // Clear form fields
            editTextRegisterName.setText("");
            editTextRegisterContact.setText("");
            editTextRegisterGsuite.setText("");
            editTextRegisterSrCode.setText("");
            editTextRegisterDepartment.setText("");
            editTextRegisterProgram.setText("");
            editTextRegisterYear.setText("");
            editTextRegisterPassword.setText("");
            editTextRegisterConfirmPassword.setText("");

            Toast.makeText(this, "Registration request sent.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        if (editTextRegisterName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterContact.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your contact number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterGsuite.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your Gsuite account", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterSrCode.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your SR Code", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterDepartment.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your department", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterProgram.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your program", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterYear.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your year level", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextRegisterPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // AsyncTask to handle the API request for registration
    private static class RegisterUserTask extends AsyncTask<String, Void, String> {
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
                    return "Registration submitted successfully.";
                } else {
                    return "Failed to register. Response code: " + responseCode;
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
