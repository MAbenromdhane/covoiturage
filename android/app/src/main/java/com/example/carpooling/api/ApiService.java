package com.example.carpooling.api;

import com.example.carpooling.models.ApiResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> loginUser(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> registerUser(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password,
        @Field("role") String role
    );
    
    @FormUrlEncoded
    @POST("create_ride.php")
    Call<ApiResponse> createRide(
        @Field("user_name") String userName,
        @Field("type") String type,
        @Field("depart") String depart,
        @Field("arrivee") String arrivee,
        @Field("date_heure") String dateHeure,
        @Field("prix") double prix,
        @Field("places") int places,
        @Field("contact") String contact,
        @Field("description") String description
    );
    
    @GET("get_rides.php")
    Call<ApiResponse> getRides(
        @Query("user_name") String userName,
        @Query("depart") String depart,
        @Query("arrivee") String arrivee
    );

    @FormUrlEncoded
    @POST("book_seat.php")
    Call<ApiResponse> bookSeat(
        @Field("ride_id") int rideId,
        @Field("passenger_name") String passengerName,
        @Field("passenger_phone") String passengerPhone
    );

    @GET("get_bookings.php")
    Call<ApiResponse> getBookings(
        @Query("driver_name") String driverName
    );

    @FormUrlEncoded
    @POST("respond_booking.php")
    Call<ApiResponse> respondToBooking(
        @Field("booking_id") int bookingId,
        @Field("action") String action
    );

    // Les autres appels seront ajoutés ici


}
