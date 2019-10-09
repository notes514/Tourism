package com.example.tourism.entity;

import java.util.Date;

public class ScenicDetails {
    private Integer scenicDetailsId;

    private Integer scenicSpotId;

    private String detailsIntroduce;

    private String containScenic;

    private Integer scenicSelled;

    private Double userScore;

    private Integer commentsNumber;

    private String recommendReasons;

    private Date departureTime;

    private String departurePlace;

    private String placeRemark;

    private String detailsDescribe;

    public Integer getScenicDetailsId() {
        return scenicDetailsId;
    }

    public void setScenicDetailsId(Integer scenicDetailsId) {
        this.scenicDetailsId = scenicDetailsId;
    }

    public Integer getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Integer scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getDetailsIntroduce() {
        return detailsIntroduce;
    }

    public void setDetailsIntroduce(String detailsIntroduce) {
        this.detailsIntroduce = detailsIntroduce;
    }

    public String getContainScenic() {
        return containScenic;
    }

    public void setContainScenic(String containScenic) {
        this.containScenic = containScenic;
    }

    public Integer getScenicSelled() {
        return scenicSelled;
    }

    public void setScenicSelled(Integer scenicSelled) {
        this.scenicSelled = scenicSelled;
    }

    public Double getUserScore() {
        return userScore;
    }

    public void setUserScore(Double userScore) {
        this.userScore = userScore;
    }

    public Integer getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(Integer commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getRecommendReasons() {
        return recommendReasons;
    }

    public void setRecommendReasons(String recommendReasons) {
        this.recommendReasons = recommendReasons;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getPlaceRemark() {
        return placeRemark;
    }

    public void setPlaceRemark(String placeRemark) {
        this.placeRemark = placeRemark;
    }

    public String getDetailsDescribe() {
        return detailsDescribe;
    }

    public void setDetailsDescribe(String detailsDescribe) {
        this.detailsDescribe = detailsDescribe;
    }
}