package com.example.carpooling.activities;

import android.os.Bundle;
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

public class MyRidesActivity extends AppCompatActivity {

    private RecyclerView rvMyRides;
    private RideAdapter rideAdapter;
    private List<Ride> rideList = new ArrayList<>();
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        rvMyRides = findViewById(R.id.rvMyRides);
        rvMyRides.setLayoutManager(new LinearLayoutManager(this));
        
        // Drivers don't see the book button, hence true for isDriver
        rideAdapter = new RideAdapter(rideList, true, null);
        rvMyRides.setAdapter(rideAdapter);

        loadMyRides();
    }

    private void loadMyRides() {
        String userName = sessionManager.getUserName();
        
        apiService.getRides(userName, "", "").enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rideList.clear();
                    if (response.body().getRides() != null) {
                        rideList.addAll(response.body().getRides());
                    }
                    rideAdapter.notifyDataSetChanged();
                    if (rideList.isEmpty()) {
                        Toast.makeText(MyRidesActivity.this, "Vous n'avez publié aucun trajet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MyRidesActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
