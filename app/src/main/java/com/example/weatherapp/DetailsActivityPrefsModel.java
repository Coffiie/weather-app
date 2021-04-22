package com.example.weatherapp;

public class DetailsActivityPrefsModel {
    String temp, humidity, pressure, realFeel;

    public DetailsActivityPrefsModel(String temp, String humidity, String pressure, String realFeel) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.realFeel = realFeel;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    public String getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(String realFeel) {
        this.realFeel = realFeel;
    }
}
