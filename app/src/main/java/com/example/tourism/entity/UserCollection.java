package com.example.tourism.entity;

public class UserCollection {
    private Integer collectionId;

    private Integer userId;

    private Integer scenicDetailsId;

    private String collectionDescribe;

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScenicDetailsId() {
        return scenicDetailsId;
    }

    public void setScenicDetailsId(Integer scenicDetailsId) {
        this.scenicDetailsId = scenicDetailsId;
    }

    public String getCollectionDescribe() {
        return collectionDescribe;
    }

    public void setCollectionDescribe(String collectionDescribe) {
        this.collectionDescribe = collectionDescribe;
    }
}