package com.example.carpooling.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.R;
import com.example.carpooling.adapters.RideAdapter;
import com.example.carpooling.api.ApiClient;
import com.example.carpooling.api.ApiService;
import com.example.carpooling.models.ApiResponse;
import com.example.carpooling.models.Ride;
import com.example.carpooling.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerDashboardActivity extends AppCompatActivity {

    private TextView tvWelcomePassenger;
    private Button btnLogout, btnSearch;
    private Spinner spinnerDeparture, spinnerDestination;
    private RecyclerView rvRides;
    private RideAdapter rideAdapter;
    private List<Ride> rideList = new ArrayList<>();
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_dashboard);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        tvWelcomePassenger = findViewById(R.id.tvWelcomePassenger);
        btnLogout = findViewById(R.id.btnLogout);
        btnSearch = findViewById(R.id.btnSearch);
        spinnerDeparture = findViewById(R.id.spinnerDeparture);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        rvRides = findViewById(R.id.rvRides);

        tvWelcomePassenger.setText("Bienvenue, " + sessionManager.getUserName());

        // Setup RecyclerView
        rvRides.setLayoutManager(new LinearLayoutManager(this));
        rideAdapter = new RideAdapter(rideList, false, new RideAdapter.OnRideClickListener() {
            @Override
            public void onBookClick(Ride ride) {
                handleBooking(ride);
            }
        });
        rvRides.setAdapter(rideAdapter);

        // Populate Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tunisia_states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeparture.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);

        // Load all rides initially
        loadRides("", "");

        btnSearch.setOnClickListener(v -> {
            String depart = spinnerDeparture.getSelectedItem().toString();
            String arrivee = spinnerDestination.getSelectedItem().toString();
            loadRides(depart, arrivee);
        });

        btnLogout.setOnClickListener(v -> {
            sessionManager.logoutUser();
            Toast.makeText(PassengerDashboardActivity.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PassengerDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadRides(String depart, String arrivee) {
        // If "Choisir..." is selected, treat as empty
        String dep = depart.contains("Choisir") ? "" : depart;
        String arr = arrivee.contains("Choisir") ? "" : arrivee;

        apiService.getRides("", dep, arr).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rideList.clear();
                    if (response.body().getRides() != null) {
                        rideList.addAll(response.body().getRides());
                    }
                    rideAdapter.notifyDataSetChanged();
                    if (rideList.isEmpty()) {
                        Toast.makeText(PassengerDashboardActivity.this, "Aucun trajet trouvé.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PassengerDashboardActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleBooking(Ride ride) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Réserver un trajet");
        builder.setMessage("Entrez votre numéro de téléphone pour que le conducteur puisse vous contacter:");

        final android.widget.EditText input = new android.widget.EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("Réserver", (dialog, which) -> {
            String phone = input.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(this, "Numéro de téléphone requis.", Toast.LENGTH_SHORT).show();
                return;
            }
            performBooking(ride, phone);
        });
        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void performBooking(Ride ride, String phone) {
        String passengerName = sessionManager.getUserName();
        apiService.bookSeat(ride.getId(), passengerName, phone).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(PassengerDashboardActivity.this, "Réservation réussie !", Toast.LENGTH_LONG).show();
                        // Refresh list
                        loadRides(spinnerDeparture.getSelectedItem().toString(), spinnerDestination.getSelectedItem().toString());
                    } else {
                        Toast.makeText(PassengerDashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PassengerDashboardActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
