package com.example.carpooling.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carpooling.R;
import com.example.carpooling.utils.SessionManager;

public class DriverDashboardActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView welcomeText;
    private Button logoutButton;
    private Button createRideButton;
    private Button viewMyRidesButton;
    private Button viewRequestsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        // Initialize SessionManager
        sessionManager = new SessionManager(this);

        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(DriverDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize views
        welcomeText = findViewById(R.id.welcomeText);
        logoutButton = findViewById(R.id.logoutButton);
        createRideButton = findViewById(R.id.createRideButton);
        viewMyRidesButton = findViewById(R.id.viewMyRidesButton);
        viewRequestsButton = findViewById(R.id.viewRequestsButton);

        // Set welcome message
        int userId = sessionManager.getUserId();
        welcomeText.setText("Welcome Driver!\nID: " + userId);

        // Setup logout button click listener
        logoutButton.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(DriverDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DriverDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Setup create ride button
        createRideButton.setOnClickListener(v -> {
            Intent intent = new Intent(DriverDashboardActivity.this, CreateRideActivity.class);
            startActivity(intent);
        });

        // Setup view my rides button
        viewMyRidesButton.setOnClickListener(v -> {
            Intent intent = new Intent(DriverDashboardActivity.this, MyRidesActivity.class);
            startActivity(intent);
        });

        // Setup view requests button
        viewRequestsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DriverDashboardActivity.this, RideRequestsActivity.class);
            startActivity(intent);
        });
    }
}