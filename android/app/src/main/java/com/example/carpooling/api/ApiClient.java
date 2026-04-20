package com.example.carpooling.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Utiliser 10.0.2.2 pour accéder à localhost depuis l'émulateur Android
    public static final String BASE_URL = "http://10.26.13.114:8081/covoiturage/backend/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
