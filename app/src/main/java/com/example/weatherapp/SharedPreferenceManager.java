package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

class SharedPrefsKeys{
    static final public String prefsName = "data";
    static final public String degreeKey = "degreesKey";
    static final public  String statusKey = "statusKey";
    static final public String realFeelKey = "realFeelKey";
    static final public  String humidityKey = "humidityKey";
    static final public String pressureKey = "pressureKey";
    static final public String latKey = "latKey";
    static final public String longKey = "longKey";
}
public class SharedPreferenceManager {

    private SharedPreferences prefs;

    SharedPreferenceManager(Context context) {
        prefs = context.getSharedPreferences(SharedPrefsKeys.prefsName, context.MODE_PRIVATE);
    }

    public void writeToStorage(OfflineStorageModel model) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsKeys.degreeKey, model.getDegrees());
        editor.putString(SharedPrefsKeys.statusKey, model.getStatus());
        editor.putString(SharedPrefsKeys.humidityKey, model.getHumidity());
        editor.putString(SharedPrefsKeys.pressureKey, model.getPressure());
        editor.putString(SharedPrefsKeys.realFeelKey, model.getRealFeel());
        editor.apply();
    }

    public OfflineStorageModel readFromStorage() {
        String degrees = prefs.getString(SharedPrefsKeys.degreeKey, "0°");
        String status = prefs.getString(SharedPrefsKeys.statusKey, "Not found");
        String realFeel = prefs.getString(SharedPrefsKeys.realFeelKey, "0°");
        String pressure = prefs.getString(SharedPrefsKeys.pressureKey, "0°");
        String humidity = prefs.getString(SharedPrefsKeys.humidityKey, "0°");
        return new OfflineStorageModel(status, degrees, humidity, pressure, realFeel);
    }

    public void writeLocationToStorage(OfflineStorageModel model){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(SharedPrefsKeys.latKey, (float)model.getUserLocation().latitude);
        editor.putFloat(SharedPrefsKeys.longKey, (float)model.getUserLocation().longitude);
        editor.apply();
    }

    public OfflineStorageModel readLocationFromStorage() {
        float lat = prefs.getFloat(SharedPrefsKeys.latKey, 41);
        float lon = prefs.getFloat(SharedPrefsKeys.longKey,42);
        return new OfflineStorageModel(new LatLng(lat,lon));
    }

    public boolean hasKey(String key){
        boolean val = prefs.contains(key);
        return val;
    }
}
