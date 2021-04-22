package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    List<LatLng> locationList;
LocationAdapter adapter;
RecyclerView rv;
FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Stetho.initializeWithDefaults(this);
        dbHelper = new DatabaseHelper(this);
        rv = findViewById(R.id.rv);
        floatingActionButton = findViewById(R.id.refreshBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    private void init(){
        locationList = getAllData();
        adapter = new LocationAdapter(this,locationList);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    public List<LatLng> getAllData() {
        final List<LatLng> locationList = new ArrayList<LatLng>();
        Cursor cursor = dbHelper.getAllData();
        while (cursor.moveToNext()) {
            double latitude, longitude;
            latitude =cursor.getDouble(0);
            longitude = cursor.getDouble(1);
            locationList.add(new LatLng(latitude,longitude));
        }
        cursor.close();
        return locationList;
//
    }
}