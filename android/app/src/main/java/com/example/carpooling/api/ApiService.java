package com.example.carpooling.api;

import com.example.carpooling.models.ApiResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> loginUser(
        @Field("email") String email,
        @Field("password") String password
    );
    
    // Les autres appels seront ajoutés ici
}
