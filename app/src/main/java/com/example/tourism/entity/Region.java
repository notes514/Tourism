package com.example.tourism.entity;

public class Region {
    //区域id
    private int regionId;
    //区域名
    private String regionName;

    public Region(int regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }


    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
