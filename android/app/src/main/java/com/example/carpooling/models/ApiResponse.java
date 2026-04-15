package com.example.carpooling.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public User getUser() { return user; }
}
