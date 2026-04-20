package com.example.carpooling.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.R;
import com.example.carpooling.adapters.BookingAdapter;
import com.example.carpooling.api.ApiClient;
import com.example.carpooling.api.ApiService;
import com.example.carpooling.models.ApiResponse;
import com.example.carpooling.models.Booking;
import com.example.carpooling.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideRequestsActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList = new ArrayList<>();
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_requests);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        rvBookings = findViewById(R.id.rvBookings);
        rvBookings.setLayoutManager(new LinearLayoutManager(this));
        
        bookingAdapter = new BookingAdapter(bookingList);
        rvBookings.setAdapter(bookingAdapter);

        loadBookings();
    }

    private void loadBookings() {
        String userName = sessionManager.getUserName();
        
        apiService.getBookings(userName).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookingList.clear();
                    if (response.body().getBookings() != null) {
                        bookingList.addAll(response.body().getBookings());
                    }
                    bookingAdapter.notifyDataSetChanged();
                    if (bookingList.isEmpty()) {
                        Toast.makeText(RideRequestsActivity.this, "Aucune demande de réservation.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RideRequestsActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
