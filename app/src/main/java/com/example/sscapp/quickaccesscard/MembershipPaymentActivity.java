package com.example.sscapp.quickaccesscard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sscapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MembershipPaymentActivity extends AppCompatActivity {
    private TextInputEditText referenceNumberEditText;
    private MaterialButton uploadReceiptButton;
    private MaterialButton submitButton;
    private ImageView receiptImageView;
    private Uri receiptImageUri;
    private TextView statusTextView;
    private boolean isSubmitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        referenceNumberEditText = findViewById(R.id.referenceNumberEditText);
        uploadReceiptButton = findViewById(R.id.uploadReceiptButton);
        submitButton = findViewById(R.id.submitButton);
        receiptImageView = findViewById(R.id.receiptImageView);
        statusTextView = findViewById(R.id.statusTextView);

        uploadReceiptButton.setOnClickListener(v -> openImagePicker());
        submitButton.setOnClickListener(v -> submitPayment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openImagePicker() {
        if (!isSubmitted) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(this, "Payment already submitted. Cannot modify.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            receiptImageUri = data.getData();
            receiptImageView.setImageURI(receiptImageUri);
        }
    }

    private void submitPayment() {
        if (isSubmitted) {
            Toast.makeText(this, "Payment already submitted. Cannot submit again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String referenceNumber = referenceNumberEditText.getText().toString();
        if (referenceNumber.isEmpty() || receiptImageUri == null) {
            Toast.makeText(this, "Please fill all fields and upload a receipt", Toast.LENGTH_SHORT).show();
            return;
        }

        isSubmitted = true;
        statusTextView.setText("Waiting for confirmation");
        statusTextView.setTextColor(getResources().getColor(R.color.yellow));
        submitButton.setEnabled(false);
        uploadReceiptButton.setEnabled(false);
        referenceNumberEditText.setEnabled(false);

        new Handler().postDelayed(() -> {
            statusTextView.setText("Verified");
            statusTextView.setTextColor(getResources().getColor(R.color.green));
        }, 5000);

        Toast.makeText(this, "Payment submitted for verification", Toast.LENGTH_LONG).show();
    }
}

