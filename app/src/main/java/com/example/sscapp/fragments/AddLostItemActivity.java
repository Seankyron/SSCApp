package com.example.sscapp.fragments;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sscapp.R;
import com.example.sscapp.models.Item;
import com.google.android.material.textfield.TextInputEditText;

public class AddLostItemActivity extends AppCompatActivity {

    private TextInputEditText editTextItemName, editTextLocation, editTextDate, editTextDescription, editTextContactInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost_item);

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Lost Item");
        }

        // Initialize views
        editTextItemName = findViewById(R.id.editTextItemName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDate = findViewById(R.id.editTextDate);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextContactInfo = findViewById(R.id.editTextContactInfo);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(v -> submitLostItem());
    }

    private void submitLostItem() {
        String name = editTextItemName.getText() != null ? editTextItemName.getText().toString().trim() : "";
        String location = editTextLocation.getText() != null ? editTextLocation.getText().toString().trim() : "";
        String date = editTextDate.getText() != null ? editTextDate.getText().toString().trim() : "";
        String description = editTextDescription.getText() != null ? editTextDescription.getText().toString().trim() : "";
        String contactInfo = editTextContactInfo.getText() != null ? editTextContactInfo.getText().toString().trim() : "";

        if (name.isEmpty() || location.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Item object
        Item newItem = new Item(name, location, date, R.drawable.image_placeholder, description, contactInfo);

        // TODO: Add the new item to your data source (e.g., database, API)

        Toast.makeText(this, "Lost item added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity and return to the previous screen
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}