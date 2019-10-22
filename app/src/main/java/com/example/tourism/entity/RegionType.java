package com.example.tourism.entity;

public class RegionType {
    private int regionTypeId;
    private String regionTypeName;

    public RegionType(int regionTypeId, String regionTypeName) {
        this.regionTypeId = regionTypeId;
        this.regionTypeName = regionTypeName;
    }

    public int getRegionTypeId() {
        return regionTypeId;
    }

    public void setRegionTypeId(int regionTypeId) {
        this.regionTypeId = regionTypeId;
    }

    public String getRegionTypeName() {
        return regionTypeName;
    }

    public void setRegionTypeName(String regionTypeName) {
        this.regionTypeName = regionTypeName;
    }
}
