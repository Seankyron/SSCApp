package com.example.sscapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sscapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class eSSCentials extends AppCompatActivity {

    private CheckBox checkBoxWalkieTalkie, checkBoxSpeaker, checkBoxMicrophone;
    private EditText editTextWalkieTalkieCount, editTextMicrophoneCount, editTextPurpose, editTextDate;
    private TextView textViewWalkieTalkieAvailable, textViewSpeakerAvailable, textViewMicrophoneAvailable;
    private Button buttonSubmit;

    private int walkieTalkiesAvailable = 12;
    private int speakersAvailable = 2;
    private int microphonesAvailable = 4;

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

        textViewWalkieTalkieAvailable = findViewById(R.id.textViewWalkieTalkieAvailable);
        textViewSpeakerAvailable = findViewById(R.id.textViewSpeakerAvailable);
        textViewMicrophoneAvailable = findViewById(R.id.textViewMicrophoneAvailable);

        buttonSubmit = findViewById(R.id.buttonSubmit);

        updateAvailability();
    }

    private void setupListeners() {
        editTextWalkieTalkieCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateWalkieTalkieAvailability();
            }
        });

        editTextMicrophoneCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateMicrophoneAvailability();
            }
        });

        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        buttonSubmit.setOnClickListener(v -> submitRequest());
    }

    private void updateAvailability() {
        textViewWalkieTalkieAvailable.setText(walkieTalkiesAvailable + " pcs available");
        textViewSpeakerAvailable.setText(speakersAvailable + " pcs available");
        textViewMicrophoneAvailable.setText(microphonesAvailable + " pcs available");

        checkBoxSpeaker.setEnabled(speakersAvailable > 0);
        if (speakersAvailable == 0) {
            checkBoxSpeaker.setChecked(false);
            textViewSpeakerAvailable.setText("Not available");
        }
    }

    private void updateWalkieTalkieAvailability() {
        int requested = 0;
        try {
            requested = Integer.parseInt(editTextWalkieTalkieCount.getText().toString());
        } catch (NumberFormatException e) {
            // Invalid input, treat as 0
        }

        if (requested > walkieTalkiesAvailable) {
            editTextWalkieTalkieCount.setError("Only " + walkieTalkiesAvailable + " available");
        } else {
            editTextWalkieTalkieCount.setError(null);
        }
    }

    private void updateMicrophoneAvailability() {
        int requested = 0;
        try {
            requested = Integer.parseInt(editTextMicrophoneCount.getText().toString());
        } catch (NumberFormatException e) {
            // Invalid input, treat as 0
        }

        if (requested > microphonesAvailable) {
            editTextMicrophoneCount.setError("Only " + microphonesAvailable + " available");
        } else {
            editTextMicrophoneCount.setError(null);
        }
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

        // Here you would typically send the request to your backend or save it locally
        // For this example, we'll just show a toast message
        Toast.makeText(this, "Request submitted successfully!", Toast.LENGTH_LONG).show();
        finish(); // Close the activity after submission
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (!checkBoxWalkieTalkie.isChecked() && !checkBoxSpeaker.isChecked() && !checkBoxMicrophone.isChecked()) {
            Toast.makeText(this, "Please select at least one item to borrow", Toast.LENGTH_SHORT).show();
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
}

