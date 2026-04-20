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
    private EditText etPrice;
    private EditText etPlaces;
    private EditText etContact;
    private EditText etComments;
    private Button btnPostRide;

    private com.example.carpooling.api.ApiService apiService;
    private com.example.carpooling.utils.SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        // Initialize managers
        sessionManager = new com.example.carpooling.utils.SessionManager(this);
        apiService = com.example.carpooling.api.ApiClient.getClient().create(com.example.carpooling.api.ApiService.class);

        // Initialize views
        spinnerDeparture = findViewById(R.id.spinnerDeparture);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etPrice = findViewById(R.id.etPrice);
        etPlaces = findViewById(R.id.etPlaces);
        etContact = findViewById(R.id.etContact);
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
                    // Format for display: DD/MM/YYYY
                    // Format for DB: YYYY-MM-DD
                    String selectedDate = String.format("%04d-%02d-%02d", year1, month1 + 1, dayOfMonth);
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
                    String selectedTime = String.format("%02d:%02d:00", hourOfDay, minute1);
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
        String priceStr = etPrice.getText().toString().trim();
        String placesStr = etPlaces.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String comments = etComments.getText().toString().trim();

        if (departure.isEmpty() || destination.isEmpty() || date.isEmpty() || time.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (departure.equals(destination)) {
            Toast.makeText(this, "La destination doit être différente du départ.", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = 0.0;
        int places = 1;

        try {
            if (!priceStr.isEmpty()) {
                price = Double.parseDouble(priceStr);
            }
            if (!placesStr.isEmpty()) {
                places = Integer.parseInt(placesStr);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer des valeurs numériques valides.", Toast.LENGTH_SHORT).show();
            return;
        }

        String dateHeure = date + " " + time;
        String userName = sessionManager.getUserName();
        String type = "offre"; // By default for this activity

        if (userName == null || userName.isEmpty()) {
            Toast.makeText(this, "Erreur: Utilisateur non connecté.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading
        btnPostRide.setEnabled(false);
        btnPostRide.setText("Publication en cours...");

        retrofit2.Call<com.example.carpooling.models.ApiResponse> call = apiService.createRide(
                userName, type, departure, destination, dateHeure, price, places, contact, comments
        );

        call.enqueue(new retrofit2.Callback<com.example.carpooling.models.ApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<com.example.carpooling.models.ApiResponse> call, retrofit2.Response<com.example.carpooling.models.ApiResponse> response) {
                btnPostRide.setEnabled(true);
                btnPostRide.setText("Publier le Trajet");
                
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(CreateRideActivity.this, "Trajet publié avec succès !", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(CreateRideActivity.this, "Erreur: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateRideActivity.this, "Erreur serveur.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.example.carpooling.models.ApiResponse> call, Throwable t) {
                btnPostRide.setEnabled(true);
                btnPostRide.setText("Publier le Trajet");
                Toast.makeText(CreateRideActivity.this, "Erreur de connexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
