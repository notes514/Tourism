package com.example.tourism.entity;

import java.io.Serializable;

public class ScenicSpot implements Serializable {
    private Integer scenicSpotId;

    private Integer regionId;

    private String scenicSpotTheme;

    private String scenicSpotPicUrl;

    private Double scenicSpotPrice;

    private Integer travelMode;

    private String startLand;

    private String endLand;

    private Integer scenicSpotState;

    private String scenicSpotDescribe;

    public ScenicSpot(Integer scenicSpotId, Integer regionId, String scenicSpotTheme,
                      String scenicSpotPicUrl, Double scenicSpotPrice, Integer travelMode,
                      String startLand, String endLand, Integer scenicSpotState, String scenicSpotDescribe) {
        this.scenicSpotId = scenicSpotId;
        this.regionId = regionId;
        this.scenicSpotTheme = scenicSpotTheme;
        this.scenicSpotPicUrl = scenicSpotPicUrl;
        this.scenicSpotPrice = scenicSpotPrice;
        this.travelMode = travelMode;
        this.startLand = startLand;
        this.endLand = endLand;
        this.scenicSpotState = scenicSpotState;
        this.scenicSpotDescribe = scenicSpotDescribe;
    }

    public Integer getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Integer scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getScenicSpotTheme() {
        return scenicSpotTheme;
    }

    public void setScenicSpotTheme(String scenicSpotTheme) {
        this.scenicSpotTheme = scenicSpotTheme;
    }

    public String getScenicSpotPicUrl() {
        return scenicSpotPicUrl;
    }

    public void setScenicSpotPicUrl(String scenicSpotPicUrl) {
        this.scenicSpotPicUrl = scenicSpotPicUrl;
    }

    public Double getScenicSpotPrice() {
        return scenicSpotPrice;
    }

    public void setScenicSpotPrice(Double scenicSpotPrice) {
        this.scenicSpotPrice = scenicSpotPrice;
    }

    public Integer getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(Integer travelMode) {
        this.travelMode = travelMode;
    }

    public String getStartLand() {
        return startLand;
    }

    public void setStartLand(String startLand) {
        this.startLand = startLand;
    }

    public String getEndLand() {
        return endLand;
    }

    public void setEndLand(String endLand) {
        this.endLand = endLand;
    }

    public Integer getScenicSpotState() {
        return scenicSpotState;
    }

    public void setScenicSpotState(Integer scenicSpotState) {
        this.scenicSpotState = scenicSpotState;
    }

    public String getScenicSpotDescribe() {
        return scenicSpotDescribe;
    }

    public void setScenicSpotDescribe(String scenicSpotDescribe) {
        this.scenicSpotDescribe = scenicSpotDescribe;
    }
}