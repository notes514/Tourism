package com.example.tourism.entity;

import java.util.Date;

public class MessagePush {
    private Integer pushId;

    private String pushContent;

    private Date pushTime;

    private String pushDescribe;

    public Integer getPushId() {
        return pushId;
    }

    public void setPushId(Integer pushId) {
        this.pushId = pushId;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushDescribe() {
        return pushDescribe;
    }

    public void setPushDescribe(String pushDescribe) {
        this.pushDescribe = pushDescribe;
    }
}