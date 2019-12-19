package com.example.enseirb_neudecknicolas_satomidavid.DataClasses;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Run {
    User user;
    Date date;
    int length;
    int seconds;
    int averageSpeed;
    int topSpeed;
    JSONObject locationList;

    public Run(User user, Date date, int length, int seconds, int averageSpeed, int topSpeed, JSONObject locationList) {
        this.user = user;
        this.date = date;
        this.length = length;
        this.seconds = seconds;
        this.averageSpeed = averageSpeed;
        this.topSpeed = topSpeed;
        this.locationList = locationList;
    }

    public User getUser() {
        return user;
    }

    public void setUsername(User user) {
        this.user = user;
    }

    public String getDateString() {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.ENGLISH);
        return df.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLengthString() {
        double ganzes = ((double)length / (double)1000);
        ganzes -= ganzes % 0.01;
        ganzes *= 100d;
        ganzes = ((int) ganzes) / 100d;
        return ganzes  + " km";
    }

    public int getLength(){
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSecondsString() {
        int min = (int)((double) seconds / (double) 60);
        int sec = (int)(seconds % 60);
        return min + " min " + sec + " s";
    }

    public int getSeconds(){
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getAverageSpeed(){
        return averageSpeed;
    }

    public String getAverageSpeedString() {
        return averageSpeed + " km/h";
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public String getTopSpeedString() {
        return topSpeed + " km/h";
    }

    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }

    public JSONObject getLocationList() {
        return locationList;
    }

    public void setLocationList(JSONObject locationList) {
        this.locationList = locationList;
    }
}



