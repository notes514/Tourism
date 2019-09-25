package com.example.tourism.entity;

public class ScenicRegion {
    private Integer regionId;

    private String regionName;

    private String regionPicUrl;

    private String regionDescribe;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionPicUrl() {
        return regionPicUrl;
    }

    public void setRegionPicUrl(String regionPicUrl) {
        this.regionPicUrl = regionPicUrl;
    }

    public String getRegionDescribe() {
        return regionDescribe;
    }

    public void setRegionDescribe(String regionDescribe) {
        this.regionDescribe = regionDescribe;
    }
}