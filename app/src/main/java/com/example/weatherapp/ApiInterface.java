package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather")
    Call<CurrentWeatherModel> getCurrentWeather(@Query("lat") String lat,@Query("lon") String lon, @Query("appid") String apiKey);
}
