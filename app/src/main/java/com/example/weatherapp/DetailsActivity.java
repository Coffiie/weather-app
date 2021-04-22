package com.example.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.weatherapp.ApiConstants.API_KEY;
import static com.example.weatherapp.ApiConstants.BASE_URL;
import static com.example.weatherapp.ApiConstants.cityParam;

public class DetailsActivity extends AppCompatActivity {

    TextView degTV, degTextTV, realFeelTV, realFeelTextTV, humidityTV, humidityTextTV, pressureTV, pressureTextTV;
    SharedPreferenceManager prefsManager;
    private ApiInterface service;
    private LocationManager mLocationManager;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        init();
    }

    private void getLocationFromSharedPrefs() {
        OfflineStorageModel model = prefsManager.readLocationFromStorage();
        currentLocation = model.userLocation;
    }

    private void init() {
        prefsManager = new SharedPreferenceManager(DetailsActivity.this);
        getLocationFromSharedPrefs();




        degTV = findViewById(R.id.degTV);
        degTextTV = findViewById(R.id.degTextTV);
        realFeelTV = findViewById(R.id.realFeelTV);
        realFeelTextTV = findViewById(R.id.realFeelTextTV);
        humidityTV = findViewById(R.id.humidityTV);
        humidityTextTV = findViewById(R.id.humidityTextTV);
        pressureTV = findViewById(R.id.pressureTV);
        pressureTextTV = findViewById(R.id.pressureTextTV);

        degTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade));
        degTextTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade));
        realFeelTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade_back));
        realFeelTextTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade_back));
        humidityTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade_back));
        humidityTextTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade_back));
        pressureTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade_back));
        pressureTextTV.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, R.anim.move_and_fade_back));


        getDataFromSharedPrefs();

        callApi();
    }

    private void getDataFromSharedPrefs(){
        OfflineStorageModel model = prefsManager.readFromStorage();
        degTV.setText(model.degrees);
        humidityTV.setText(model.humidity);
        pressureTV.setText(model.pressure);
        realFeelTV.setText(model.realFeel);
    }

    private void callApi() {
        Retrofit retr = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retr.create(ApiInterface.class);
        retrofit2.Call<CurrentWeatherModel> call = service.getCurrentWeather(String.valueOf(currentLocation.latitude),String.valueOf(currentLocation.longitude), API_KEY);

        call.enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(retrofit2.Call<CurrentWeatherModel> call, Response<CurrentWeatherModel> response) {
                CurrentWeatherModel model = response.body();
                String[] val = String.valueOf(model.getMain().getTemp() - 273.15).split("\\.");
                String temp = val[0] + "°";

                val = String.valueOf(model.getMain().getFeelsLike() - 273.15).split("\\.");
                String realFeel = val[0] + "°";

                String humidity = String.valueOf(model.getMain().getHumidity());
                String status = model.getWeather().get(0).getMain();
                String pressure = String.valueOf(model.getMain().getPressure());
                degTV.setText(temp);
                realFeelTV.setText(realFeel);
                humidityTV.setText(humidity);
                pressureTV.setText(pressure);
                prefsManager.writeToStorage(new OfflineStorageModel(status, temp, humidity, pressure, realFeel));

            }

            @Override
            public void onFailure(Call<CurrentWeatherModel> call, Throwable t) {

            }
        });

    }

}
