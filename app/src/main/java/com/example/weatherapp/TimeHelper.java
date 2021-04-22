package com.example.weatherapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeHelper {

    public String currentDate;
    public String currentTime;

    TimeHelper(){
        currentDate = getCurrentDate();
        currentTime = getCurrentTime();
    }

    private String getCurrentTime(){
        DateFormat df = new SimpleDateFormat("HH:mm");
        String formatted = df.format(Calendar.getInstance().getTime()) ;
        return formatted;
    }

    private String getCurrentDate(){
        DateFormat df = new SimpleDateFormat("EEEE, d MMMM");
        String formatted = df.format(Calendar.getInstance().getTime());
        return formatted;
    }
}
