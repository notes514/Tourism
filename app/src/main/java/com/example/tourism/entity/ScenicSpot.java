package com.example.tourism.entity;

public class ScenicSpot {
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