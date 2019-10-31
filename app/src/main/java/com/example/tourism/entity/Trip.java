package com.example.tourism.entity;

public class Trip {
    private Integer tripId;

    private Integer userId;

    private Integer scenicSpotId;

    private Integer scenicDetailsId;

    private String tripInformation;

    private Double price;

    private String remark;

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Integer scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public Integer getScenicDetailsId() {
        return scenicDetailsId;
    }

    public void setScenicDetailsId(Integer scenicDetailsId) {
        this.scenicDetailsId = scenicDetailsId;
    }

    public String getTripInformation() {
        return tripInformation;
    }

    public void setTripInformation(String tripInformation) {
        this.tripInformation = tripInformation;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}