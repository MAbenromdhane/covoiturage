package com.example.carpooling.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpooling.R;
import com.example.carpooling.utils.SessionManager;

public class PassengerDashboardActivity extends AppCompatActivity {

    private TextView tvWelcomePassenger;
    private Button btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_dashboard);

        sessionManager = new SessionManager(this);

        tvWelcomePassenger = findViewById(R.id.tvWelcomePassenger);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                Toast.makeText(PassengerDashboardActivity.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PassengerDashboardActivity.this, LoginActivity.class);
                // Clear the back stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
