package com.example.carpooling.models;

import com.google.gson.annotations.SerializedName;

public class Ride {
    @SerializedName("id")
    private int id;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("type")
    private String type;

    @SerializedName("depart")
    private String depart;

    @SerializedName("arrivee")
    private String arrivee;

    @SerializedName("date_heure")
    private String dateHeure;

    @SerializedName("prix")
    private double prix;

    @SerializedName("places")
    private int places;

    @SerializedName("contact")
    private String contact;

    @SerializedName("description")
    private String description;

    @SerializedName("statut")
    private String statut;

    // Getters
    public int getId() { return id; }
    public String getUserName() { return userName; }
    public String getType() { return type; }
    public String getDepart() { return depart; }
    public String getArrivee() { return arrivee; }
    public String getDateHeure() { return dateHeure; }
    public double getPrix() { return prix; }
    public int getPlaces() { return places; }
    public String getContact() { return contact; }
    public String getDescription() { return description; }
    public String getStatut() { return statut; }
}
