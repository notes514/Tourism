package com.example.tourism.entity;

import java.util.Date;

public class AmountRecharge {
    private Integer rechargeId;

    private Integer userId;

    private Integer paymentMode;

    private String commodityDescription;

    private String rechargeObject;

    private Double faceValue;

    private String transactionObject;

    private Date rechargeTime;

    private String orderNumber;

    private String billClassification;

    private String remark;

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCommodityDescription() {
        return commodityDescription;
    }

    public void setCommodityDescription(String commodityDescription) {
        this.commodityDescription = commodityDescription;
    }

    public String getRechargeObject() {
        return rechargeObject;
    }

    public void setRechargeObject(String rechargeObject) {
        this.rechargeObject = rechargeObject;
    }

    public Double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    public String getTransactionObject() {
        return transactionObject;
    }

    public void setTransactionObject(String transactionObject) {
        this.transactionObject = transactionObject;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBillClassification() {
        return billClassification;
    }

    public void setBillClassification(String billClassification) {
        this.billClassification = billClassification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}