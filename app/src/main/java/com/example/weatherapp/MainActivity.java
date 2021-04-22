package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.weatherapp.ApiConstants.API_KEY;
import static com.example.weatherapp.ApiConstants.BASE_URL;
import static com.example.weatherapp.ApiConstants.cityParam;

public class MainActivity extends AppCompatActivity {
    private ApiInterface service;
    private TimeHelper timeHelper;
    TextView currentTimeTv, currentDateTv, degreeTV, statusTV;
    Timer timer;
    private LocationManager mLocationManager;
    SharedPreferenceManager prefsManager;
    ImageButton imageBtn, historyBtn;
    LatLng currentLocation = new LatLng(41,42);

    //    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void init() {
        prefsManager = new SharedPreferenceManager(MainActivity.this);

        currentTimeTv = findViewById(R.id.currentTimeTV);
        imageBtn = findViewById(R.id.searchIconButton);
        currentDateTv = findViewById(R.id.currentDateTV);
        statusTV = findViewById(R.id.statusTV);
        degreeTV = findViewById(R.id.degreeTV);
        historyBtn = findViewById(R.id.historyIconButton);

        getDataFromSharedPrefs();
        getLocationFromSharedPrefs();


        currentTimeTv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_and_fade));
        currentDateTv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_and_fade));
        statusTV.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_and_fade_back));
        degreeTV.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_and_fade_back));
        updateTimeEverySecond();




        callApi();

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open google maps activity
                openHistoryActivity();
            }
        });
        degreeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to details page
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open google maps activity
                openMapsActivity();
            }
        });
    }

    private void openHistoryActivity(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void getLocationFromSharedPrefs() {
        OfflineStorageModel model = prefsManager.readLocationFromStorage();
        currentLocation = model.userLocation;
    }

    private void getDataFromSharedPrefs() {
        OfflineStorageModel model = prefsManager.readFromStorage();
        degreeTV.setText(model.degrees);
        statusTV.setText(model.status);
    }

    private void callApi() {
        Retrofit retr = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retr.create(ApiInterface.class);
        Call<CurrentWeatherModel> call = service.getCurrentWeather(String.valueOf(currentLocation.latitude), String.valueOf(currentLocation.longitude), API_KEY);

        call.enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(Call<CurrentWeatherModel> call, Response<CurrentWeatherModel> response) {
                CurrentWeatherModel model = response.body();
                String[] val = String.valueOf(model.getMain().getTemp() - 273.15).split("\\.");
                String temp = val[0] + "°";

                val = String.valueOf(model.getMain().getFeelsLike() - 273.15).split("\\.");
                String realFeel = val[0] + "°";

                String humidity = String.valueOf(model.getMain().getHumidity());
                String status = model.getWeather().get(0).getMain();
                String pressure = String.valueOf(model.getMain().getPressure());
                degreeTV.setText(temp);
                statusTV.setText(status);
                prefsManager.writeToStorage(new OfflineStorageModel(status, temp, humidity, pressure, realFeel));

            }

            @Override
            public void onFailure(Call<CurrentWeatherModel> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void updateTimeEverySecond() {
        timer = new Timer();
        int milliseconds = 1000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeHelper = new TimeHelper();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentDateTv.setText(timeHelper.currentDate);
                        currentTimeTv.setText(timeHelper.currentTime);
                    }
                });
            }
        }, 0, milliseconds);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}