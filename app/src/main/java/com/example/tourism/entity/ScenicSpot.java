package com.example.tourism.entity;

import java.io.Serializable;

public class ScenicSpot implements Serializable {
    private Integer scenicSpotId;

    private Integer regionId;

    private String scenicSpotPicUrl;

    private String scenicSpotTheme;

    private Integer travelMode;

    private Double scenicSpotPrice;

    private Double scenicSpotScore;

    private Integer numberOfTourists;

    private Integer scenicSpotSold;

    private String startLand;

    private String endLand;

    private String tourCity;

    private Integer scenicSpotNumber;

    private String scenicSpotShop;

    private Integer scenicSpotState;

    private String scenicSpotExplain;

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

    public String getScenicSpotPicUrl() {
        return scenicSpotPicUrl;
    }

    public void setScenicSpotPicUrl(String scenicSpotPicUrl) {
        this.scenicSpotPicUrl = scenicSpotPicUrl;
    }

    public String getScenicSpotTheme() {
        return scenicSpotTheme;
    }

    public void setScenicSpotTheme(String scenicSpotTheme) {
        this.scenicSpotTheme = scenicSpotTheme;
    }

    public Integer getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(Integer travelMode) {
        this.travelMode = travelMode;
    }

    public Double getScenicSpotPrice() {
        return scenicSpotPrice;
    }

    public void setScenicSpotPrice(Double scenicSpotPrice) {
        this.scenicSpotPrice = scenicSpotPrice;
    }

    public Double getScenicSpotScore() {
        return scenicSpotScore;
    }

    public void setScenicSpotScore(Double scenicSpotScore) {
        this.scenicSpotScore = scenicSpotScore;
    }

    public Integer getNumberOfTourists() {
        return numberOfTourists;
    }

    public void setNumberOfTourists(Integer numberOfTourists) {
        this.numberOfTourists = numberOfTourists;
    }

    public Integer getScenicSpotSold() {
        return scenicSpotSold;
    }

    public void setScenicSpotSold(Integer scenicSpotSold) {
        this.scenicSpotSold = scenicSpotSold;
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

    public String getTourCity() {
        return tourCity;
    }

    public void setTourCity(String tourCity) {
        this.tourCity = tourCity;
    }

    public Integer getScenicSpotNumber() {
        return scenicSpotNumber;
    }

    public void setScenicSpotNumber(Integer scenicSpotNumber) {
        this.scenicSpotNumber = scenicSpotNumber;
    }

    public String getScenicSpotShop() {
        return scenicSpotShop;
    }

    public void setScenicSpotShop(String scenicSpotShop) {
        this.scenicSpotShop = scenicSpotShop;
    }

    public Integer getScenicSpotState() {
        return scenicSpotState;
    }

    public void setScenicSpotState(Integer scenicSpotState) {
        this.scenicSpotState = scenicSpotState;
    }

    public String getScenicSpotExplain() {
        return scenicSpotExplain;
    }

    public void setScenicSpotExplain(String scenicSpotExplain) {
        this.scenicSpotExplain = scenicSpotExplain;
    }

    public String getScenicSpotDescribe() {
        return scenicSpotDescribe;
    }

    public void setScenicSpotDescribe(String scenicSpotDescribe) {
        this.scenicSpotDescribe = scenicSpotDescribe;
    }
}