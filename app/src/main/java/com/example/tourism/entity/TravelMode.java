package com.example.tourism.entity;

public class TravelMode {
    private int travelModeId;
    private String travelModeName;

    public TravelMode(int travelModeId, String travelModeName) {
        this.travelModeId = travelModeId;
        this.travelModeName = travelModeName;
    }

    public int getTravelModeId() {
        return travelModeId;
    }

    public String getTravelModeName() {
        return travelModeName;
    }
}
