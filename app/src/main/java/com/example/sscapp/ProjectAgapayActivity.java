package com.example.sscapp;

import android.net.Uri;
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

import com.example.sscapp.models.PrintRequest;
import com.example.sscapp.utils.FileUtils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

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
                    String fileName = FileUtils.getFileName(this, uri);
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

        // Set up paper size dropdown
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
            PrintRequest request = new PrintRequest(
                    selectedFileUri,
                    paperSizeDropdown.getText().toString(),
                    Integer.parseInt(numberOfCopiesEditText.getText().toString()),
                    dateOfClaimingEditText.getText().toString(),
                    remarksEditText.getText().toString()
            );

            // TODO: Send the print request to your backend or process it locally
            Toast.makeText(this, "Print request submitted successfully", Toast.LENGTH_SHORT).show();
            finish();
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
}