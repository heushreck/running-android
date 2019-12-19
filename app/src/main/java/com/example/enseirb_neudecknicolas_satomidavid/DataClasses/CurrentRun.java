package com.example.enseirb_neudecknicolas_satomidavid.DataClasses;

import android.location.Location;

import java.util.Date;

public class CurrentRun {
    private Date time;
    private Location location;

    public CurrentRun(Date time, Location location){

        this.time = time;
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
