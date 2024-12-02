package com.example.sscapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sscapp.R;
import com.example.sscapp.utils.FileUtils;
import com.google.android.material.button.MaterialButton;

public class reSSCue extends AppCompatActivity {

    private static final int PICK_COR_FILE = 1;
    private static final int PICK_GRADES_FILE = 2;
    private static final int PICK_LOI_FILE = 3;

    private Uri corFileUri, gradesFileUri, loiFileUri;

    private Button uploadCorButton, uploadGradesButton, uploadLoiButton;
    private TextView corFileNameTextView, gradesFileNameTextView, loiFileNameTextView;
    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resscue);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Initialize views
        uploadCorButton = findViewById(R.id.uploadCorButton);
        uploadGradesButton = findViewById(R.id.uploadGradesButton);
        uploadLoiButton = findViewById(R.id.uploadLoiButton);

        corFileNameTextView = findViewById(R.id.corFileNameTextView);
        gradesFileNameTextView = findViewById(R.id.gradesFileNameTextView);
        loiFileNameTextView = findViewById(R.id.loiFileNameTextView);

        submitButton = findViewById(R.id.submitResscueRequestButton);

        // Setup file upload buttons
        setupFileUploadListeners();

        // Setup submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitApplication();
            }
        });
    }

    private void setupFileUploadListeners() {
        uploadCorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker(PICK_COR_FILE);
            }
        });

        uploadGradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker(PICK_GRADES_FILE);
            }
        });

        uploadLoiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker(PICK_LOI_FILE);
            }
        });
    }

    private void openFilePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            String fileName = getFileName(fileUri);

            switch (requestCode) {
                case PICK_COR_FILE:
                    corFileUri = fileUri;
                    corFileNameTextView.setText(fileName);
                    break;
                case PICK_GRADES_FILE:
                    gradesFileUri = fileUri;
                    gradesFileNameTextView.setText(fileName);
                    break;
                case PICK_LOI_FILE:
                    loiFileUri = fileUri;
                    loiFileNameTextView.setText(fileName);
                    break;
            }
        }
    }


    private String getFileName(Uri uri) {
        return FileUtils.getFileName(this, uri);
    }

    private void submitApplication() {
        // Check if all files are uploaded
        if (corFileUri == null || gradesFileUri == null || loiFileUri == null) {
            Toast.makeText(this, "Please upload all required documents", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement actual file upload and application submission logic
        // This might involve sending the files to a server, saving locally, etc.
        Toast.makeText(this, "ReSSCue application submitted. Admin will review your application.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

