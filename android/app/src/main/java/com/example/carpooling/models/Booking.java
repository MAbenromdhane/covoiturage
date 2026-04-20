package com.example.carpooling.models;

import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("id")
    private int id;

    @SerializedName("ride_id")
    private int rideId;

    @SerializedName("passenger_name")
    private String passengerName;

    @SerializedName("passenger_phone")
    private String passengerPhone;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String createdAt;

    // Extra fields from join
    @SerializedName("depart")
    private String depart;

    @SerializedName("arrivee")
    private String arrivee;

    @SerializedName("date_heure")
    private String dateHeure;

    // Getters
    public int getId() { return id; }
    public int getRideId() { return rideId; }
    public String getPassengerName() { return passengerName; }
    public String getPassengerPhone() { return passengerPhone; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
    public String getDepart() { return depart; }
    public String getArrivee() { return arrivee; }
    public String getDateHeure() { return dateHeure; }
}
