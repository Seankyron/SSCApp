package com.example.sscapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sscapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ZoomConnect extends AppCompatActivity {

    private CalendarView zoomCalendarView;
    private AutoCompleteTextView timeSlotAutoComplete;
    private ArrayAdapter<String> timeSlotAdapter;
    private EditText purposeEditText;
    private Button submitReservationButton;
    private String selectedDate;
    private String selectedTimeSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_connect);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Views
        zoomCalendarView = findViewById(R.id.zoomCalendarView);
        timeSlotAutoComplete = findViewById(R.id.timeSlotSpinner);
        purposeEditText = findViewById(R.id.purposeEditText);
        submitReservationButton = findViewById(R.id.submitZoomReservationButton);

        // Setup Calendar View
        setupCalendarView();

        // Setup Time Slot Dropdown
        setupTimeSlotDropdown();

        // Setup Submit Button
        setupSubmitButton();
    }



    @SuppressLint("ResourceType")
    private void setupCalendarView() {
        // Disable past dates
        zoomCalendarView.setMinDate(System.currentTimeMillis() - 1000);

        // Programmatically set date text color and style
        zoomCalendarView.setDateTextAppearance(R.style.CalendarViewDateCustomText);
        zoomCalendarView.setWeekDayTextAppearance(R.style.CalendarViewWeekCustomText);


        // Set selected date colors
        zoomCalendarView.setSelectedDateVerticalBar(getResources().getColor(R.color.red_primary));
        zoomCalendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.red_primary));

        zoomCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Format the selected date
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                selectedDate = dateFormat.format(selectedCalendar.getTime());

                // TODO: Check availability of the date and update UI accordingly
                // For example, you might want to check if the date is fully booked
                checkDateAvailability(selectedDate);
            }
        });
    }

    private void setupTimeSlotDropdown() {
        List<String> timeSlots = new ArrayList<>();
        timeSlots.add("Select Time Slot");
        timeSlots.add("8:00 AM - 9:00 AM");
        timeSlots.add("9:00 AM - 10:00 AM");
        timeSlots.add("10:00 AM - 11:00 AM");
        timeSlots.add("11:00 AM - 12:00 PM");
        timeSlots.add("12:00 PM - 1:00 PM");
        timeSlots.add("1:00 PM - 2:00 PM");
        timeSlots.add("2:00 PM - 3:00 PM");
        timeSlots.add("3:00 PM - 4:00 PM");
        timeSlots.add("4:00 PM - 5:00 PM");

        timeSlotAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, timeSlots);
        timeSlotAutoComplete.setAdapter(timeSlotAdapter);

        timeSlotAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedTimeSlot = parent.getItemAtPosition(position).toString();
                } else {
                    selectedTimeSlot = null;
                }
            }
        });
    }

    private void setupSubmitButton() {
        submitReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateReservation()) {
                    submitZoomReservation();
                }
            }
        });
    }

    private boolean validateReservation() {
        if (selectedDate == null) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedTimeSlot == null) {
            Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
            return false;
        }

        String purpose = purposeEditText.getText().toString().trim();
        if (purpose.isEmpty()) {
            Toast.makeText(this, "Please describe the purpose of your reservation", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void submitZoomReservation() {
        // TODO: Implement actual reservation submission logic
        // This might involve sending data to a backend service
        Toast.makeText(this, "Reservation submitted for review", Toast.LENGTH_SHORT).show();

        // Optional: Clear form or navigate away
        resetForm();
    }

    private void resetForm() {
        zoomCalendarView.setDate(System.currentTimeMillis());
        timeSlotAutoComplete.setText("Select Time Slot", false);
        purposeEditText.setText("");
        selectedDate = null;
        selectedTimeSlot = null;
    }

    private void checkDateAvailability(String date) {
        // TODO: Implement actual availability checking logic
        // This is a placeholder implementation
        // You would typically check against a backend service or local database

        // Example mock logic
        if (isSomeConditionMet(date)) {
            // Date is fully booked
            zoomCalendarView.setDateTextAppearance(R.style.FullyBookedDateStyle);
        } else {
            // Date is available
            zoomCalendarView.setDateTextAppearance(R.style.AvailableDateStyle);
        }
    }

    private boolean isSomeConditionMet(String date) {
        // Placeholder method - replace with actual availability checking logic
        return false;
    }
}