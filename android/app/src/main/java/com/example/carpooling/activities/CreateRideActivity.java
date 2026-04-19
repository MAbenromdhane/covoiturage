package com.example.carpooling.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpooling.R;

import java.util.Calendar;

public class CreateRideActivity extends AppCompatActivity {

    private Spinner spinnerDeparture;
    private Spinner spinnerDestination;
    private EditText etDate;
    private EditText etTime;
    private EditText etComments;
    private Button btnPostRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        // Initialize views
        spinnerDeparture = findViewById(R.id.spinnerDeparture);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etComments = findViewById(R.id.etComments);
        btnPostRide = findViewById(R.id.btnPostRide);

        // Populate the Spinners with the states of Tunisia
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tunisia_states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDeparture.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);

        // Set up Date Picker
        etDate.setOnClickListener(v -> showDatePicker());

        // Set up Time Picker
        etTime.setOnClickListener(v -> showTimePicker());

        // Set up Post Button
        btnPostRide.setOnClickListener(v -> handlePostRide());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CreateRideActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    // +1 because January is 0
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    etDate.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                CreateRideActivity.this,
                (view, hourOfDay, minute1) -> {
                    String selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                    etTime.setText(selectedTime);
                },
                hour, minute, true); // true for 24-hour time format
        timePickerDialog.show();
    }

    private void handlePostRide() {
        String departure = spinnerDeparture.getSelectedItem() != null ? spinnerDeparture.getSelectedItem().toString() : "";
        String destination = spinnerDestination.getSelectedItem() != null ? spinnerDestination.getSelectedItem().toString() : "";
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String comments = etComments.getText().toString().trim();

        if (departure.isEmpty() || destination.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (departure.equals(destination)) {
            Toast.makeText(this, "La destination doit être différente du départ.", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Replace with actual ApiService call
        Toast.makeText(this, "Simulation: Trajet proposé de " + departure + " vers " + destination, Toast.LENGTH_LONG).show();

        // Optional: Close activity on successful post
        finish();
    }
}
