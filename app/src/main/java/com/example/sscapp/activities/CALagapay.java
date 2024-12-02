package com.example.sscapp.activities;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sscapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CALagapay extends AppCompatActivity {

    private GridView calculatorGridView;
    private TextInputEditText purposeEditText;
    private TextInputEditText dateOfClaimingEditText;
    private MaterialButton submitButton;

    private boolean[] calculatorAvailability = new boolean[12];
    private int selectedCalculator = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calagapay);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        calculatorGridView = findViewById(R.id.calculatorGridView);
        purposeEditText = findViewById(R.id.purposeEditText);
        dateOfClaimingEditText = findViewById(R.id.dateOfClaimingEditText);
        submitButton = findViewById(R.id.submitCalculatorRequestButton);

        // Initialize all calculators as available
        for (int i = 0; i < calculatorAvailability.length; i++) {
            calculatorAvailability[i] = true;
        }

        // Set up grid view
        CalculatorGridAdapter adapter = new CalculatorGridAdapter();
        calculatorGridView.setAdapter(adapter);

        // Set up date picker
        dateOfClaimingEditText.setOnClickListener(v -> showDatePickerDialog());

        // Submit button listener
        submitButton.setOnClickListener(v -> submitCalculatorRequest());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    dateOfClaimingEditText.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void submitCalculatorRequest() {
        String purpose = purposeEditText.getText().toString().trim();
        String claimDate = dateOfClaimingEditText.getText().toString().trim();

        if (purpose.isEmpty()) {
            purposeEditText.setError("Please enter purpose of borrowing");
            return;
        }

        if (claimDate.isEmpty()) {
            dateOfClaimingEditText.setError("Please select a date");
            return;
        }

        if (selectedCalculator == -1) {
            Toast.makeText(this, "Please select an available calculator", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Get calculator " + (selectedCalculator + 1) + " at SSC Office. Bring valid ID on " + claimDate, Toast.LENGTH_LONG).show();
    }

    private class CalculatorGridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LinearLayout layout;
            if (convertView == null) {
                layout = new LinearLayout(CALagapay.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new GridView.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                layout = (LinearLayout) convertView;
            }

            // Clear any existing views in the layout
            layout.removeAllViews();

            // Create and add the ImageView
            ImageView imageView = new ImageView(CALagapay.this);
            int size = getResources().getDimensionPixelSize(R.dimen.calculator_grid_item_size);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(R.drawable.ic_calculator);

            // Set color filter based on availability
            if (calculatorAvailability[position]) {
                imageView.setColorFilter(getResources().getColor(R.color.gray_light), PorterDuff.Mode.SRC_IN);
            } else {
                imageView.setColorFilter(getResources().getColor(R.color.red_primary), PorterDuff.Mode.SRC_IN);
            }

            // Create and add the TextView for the row number
            TextView textView = new TextView(CALagapay.this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(String.valueOf(position + 1));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(getResources().getColor(R.color.gray_dark)); // Add this line

            // Add views to the layout
            layout.addView(imageView);
            layout.addView(textView);

            layout.setOnClickListener(v -> {
                if (!calculatorAvailability[position]) {
                    Toast.makeText(CALagapay.this,
                            "Calculator " + (position + 1) + " is not available",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Deselect previously selected calculator
                if (selectedCalculator != -1) {
                    calculatorAvailability[selectedCalculator] = true;
                }

                // Select new calculator
                selectedCalculator = position;
                calculatorAvailability[position] = false;
                notifyDataSetChanged();
            });

            return layout;
        }
    }
}

