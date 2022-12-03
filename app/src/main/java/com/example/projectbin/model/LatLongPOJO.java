package com.example.projectbin.model;

public class LatLongPOJO {
    double latitude;
    double longitude;

    public LatLongPOJO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
