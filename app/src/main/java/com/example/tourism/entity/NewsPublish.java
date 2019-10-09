package com.example.tourism.entity;

import java.util.Date;

public class NewsPublish {
    private Integer publishId;

    private Integer userId;

    private String publishContent;

    private String publishPicUrl;

    private Date publishTime;

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPublishContent() {
        return publishContent;
    }

    public void setPublishContent(String publishContent) {
        this.publishContent = publishContent;
    }

    public String getPublishPicUrl() {
        return publishPicUrl;
    }

    public void setPublishPicUrl(String publishPicUrl) {
        this.publishPicUrl = publishPicUrl;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}