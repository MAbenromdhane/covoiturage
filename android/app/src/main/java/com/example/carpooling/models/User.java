package com.example.carpooling.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    
    @SerializedName("nom")
    private String nom;
    
    @SerializedName("email")
    private String email;
    
    @SerializedName("role")
    private String role; // "passager" ou "conducteur"

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
