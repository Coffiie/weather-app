package com.example.weatherapp;

import com.google.android.gms.maps.model.LatLng;

public class OfflineStorageModel {
    String status, degrees, realFeel, humidity, pressure;
    LatLng userLocation;

    public OfflineStorageModel(String status, String degrees, String humidity, String pressure, String realFeel) {
        this.status = status;
        this.degrees = degrees;
        this.pressure = pressure;
        this.humidity = humidity;
        this.realFeel = realFeel;
    }

    public OfflineStorageModel(LatLng location){
        this.userLocation = location;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public String getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(String realFeel) {
        this.realFeel = realFeel;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }
}
