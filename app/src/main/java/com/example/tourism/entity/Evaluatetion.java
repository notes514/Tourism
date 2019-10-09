package com.example.tourism.entity;

import java.util.Date;

public class Evaluatetion {
    private Integer evaluatetionId;

    private Integer userId;

    private Integer scenicDetailsId;

    private Integer orderDetailsId;

    private String evaluatetionTheme;

    private String evaluatetionContent;

    private Double evaluatetionIntegral;

    private Date evaluatetionTime;

    public Integer getEvaluatetionId() {
        return evaluatetionId;
    }

    public void setEvaluatetionId(Integer evaluatetionId) {
        this.evaluatetionId = evaluatetionId;
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

    public Integer getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Integer orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getEvaluatetionTheme() {
        return evaluatetionTheme;
    }

    public void setEvaluatetionTheme(String evaluatetionTheme) {
        this.evaluatetionTheme = evaluatetionTheme;
    }

    public String getEvaluatetionContent() {
        return evaluatetionContent;
    }

    public void setEvaluatetionContent(String evaluatetionContent) {
        this.evaluatetionContent = evaluatetionContent;
    }

    public Double getEvaluatetionIntegral() {
        return evaluatetionIntegral;
    }

    public void setEvaluatetionIntegral(Double evaluatetionIntegral) {
        this.evaluatetionIntegral = evaluatetionIntegral;
    }

    public Date getEvaluatetionTime() {
        return evaluatetionTime;
    }

    public void setEvaluatetionTime(Date evaluatetionTime) {
        this.evaluatetionTime = evaluatetionTime;
    }
}