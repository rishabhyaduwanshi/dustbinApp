package com.example.projectbin.model;

import java.util.Date;

public class Dustbin {
    private double latitude;
    private double longitude;
    private String image;

    public Dustbin(double latitude, double longitude,String image) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
