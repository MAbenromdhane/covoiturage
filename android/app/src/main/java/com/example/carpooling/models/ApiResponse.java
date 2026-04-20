package com.example.carpooling.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class ApiResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("rides")
    private List<Ride> rides;

    @SerializedName("bookings")
    private List<Booking> bookings;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public User getUser() { return user; }
    public List<Ride> getRides() { return rides; }
    public List<Booking> getBookings() { return bookings; }


}
